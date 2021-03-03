package xyz.hyunto.core.interceptor

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.cache.CacheKey
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.BoundSql
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.mapping.SqlCommandType
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.RowBounds
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import xyz.hyunto.core.interceptor.annotations.ConsistencyBulkCheck
import xyz.hyunto.core.interceptor.annotations.ConsistencyCheck
import xyz.hyunto.core.interceptor.annotations.QueryParam
import xyz.hyunto.core.interceptor.enums.Action
import xyz.hyunto.core.interceptor.enums.TableName
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class, CacheKey::class, BoundSql::class])
)
class ConsistencyCheckInterceptor @Autowired constructor(
	private val kafkaTemplate: KafkaTemplate<String, String>
) : Interceptor {

	companion object {
		val ACCEPTED_SQL_COMMAND_TYPES = listOf(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE)
		val ACTIVE_DATABASE = DatabaseType.MySQL1
	}

	private fun setDataSource() {
		DatabaseTypeHolder.set(ACTIVE_DATABASE)
	}

	override fun intercept(invocation: Invocation): Any {
		println("### ConsistencyCheckInterceptor ###")
		if (ACCEPTED_SQL_COMMAND_TYPES.contains(getMappedStatement(invocation).sqlCommandType)) {
			val result = invocation.proceed()
			if (DatabaseTypeHolder.get() == DatabaseType.MySQL1 || result == 0) return result

			val tableName: TableName?
			val queryName: String?
			val queryParams = when (val annotation = getAnnotation(invocation)) {
				is ConsistencyCheck -> {
					tableName = annotation.tableName
					queryName = annotation.queryName
					getQueryParams(invocation, annotation)
				}
				is ConsistencyBulkCheck -> {
					tableName = annotation.tableName
					queryName = annotation.queryName
					getBulkQueryParams(invocation, annotation)
				}
				else -> return result
			}

			queryParams.takeIf { it.isNotEmpty() }?.run {
				val message = ConsistencyCheckQueueMessage(
					tableName = tableName,
					action = Action.fromSqlCommandType(getMappedStatement(invocation).sqlCommandType)!!,
					queryName = queryName,
					queryParams = this,
					activeDatabase = ACTIVE_DATABASE,
					executionDateTime = ZonedDateTime.now()
				)
				val objectMapper = getObjectMapper()
				println("카프카 메시지 전송 : ${objectMapper.writeValueAsString(message)}")
				kafkaTemplate.send("dual_write_check", "dual_write_check", objectMapper.writeValueAsString(message))
			}

			return result
		} else {
			setDataSource()
			val result = invocation.proceed()
			DatabaseTypeHolder.clear()
			return result
		}
	}

	private fun getObjectMapper(): ObjectMapper {
		val javaTimeModule = JavaTimeModule()
		javaTimeModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
		javaTimeModule.addDeserializer(ZonedDateTime::class.java, object: JsonDeserializer<ZonedDateTime>() {
			override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): ZonedDateTime {
				return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatterBuilder()
					.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
					.optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd() // ISO-8601
					.optionalStart().appendOffset("+HH:MM", "Z").optionalEnd() // ISO-8601
					.optionalStart().appendOffset("+HHMM", "+0000").optionalEnd() // legacy
					.toFormatter()
				)
			}

		})

		val objectMapper = ObjectMapper()
		objectMapper.registerModule(KotlinModule())
		objectMapper.registerModule(javaTimeModule)
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //모르는 프라퍼티에 대해서는 무시..
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 표준 사용
		return objectMapper
	}

	private fun getBulkQueryParams(invocation: Invocation, annotation: ConsistencyBulkCheck): List<List<Any?>> {
		val parameterMap = getParameterMap(invocation)
		val results = mutableListOf<List<Any?>>()
		annotation.queryParam.run {
			val values = parameterMap[this.name] ?: return@run
			if(values is ArrayList<*>) {
				values.forEach{
					results.add(getSubParamValue(it, this))
				}
			}
		}
		return results
	}

	private fun getQueryParams(invocation: Invocation, annotation: ConsistencyCheck): List<List<Any?>> {
		val parameterMap = getParameterMap(invocation)
		val results = mutableListOf<List<Any?>>()
		val result = mutableListOf<Any?>()
		annotation.queryParams.forEach { param ->
			val value = parameterMap[param.name] ?: return@forEach
			if (param.subQueryParams.isEmpty()) {
				result.add(value)
			} else {
				if (value is ArrayList<*>) {
					val temp = mutableListOf<Any>()
					value.forEach {
						temp.add(getSubParamValue(it, param))
					}
					result.add(temp)
				} else {
					results.add(getSubParamValue(value, param))
					return@forEach
				}
			}
		}
		results.add(result)
		return results.filterNot { it.isEmpty() }
	}

	private fun getSubParamValue(instance: Any, param: QueryParam): List<Any?> {
		val results = mutableListOf<Any?>()
		param.subQueryParams.forEach { subParam ->
			results.add(getParamValue(instance, subParam.name))
		}
		return results
	}

	private fun getParamValue(instance: Any, name: String): Any? {
		val property = instance::class.members.first { it.name == name } as KProperty1<Any, *>
		return property.get(instance)
	}

	private fun getAnnotation(invocation: Invocation): Any? {
		val mappedStatement = getMappedStatement(invocation)
		val className = mappedStatement.id.substringBeforeLast(".")
		val methodName = mappedStatement.id.substringAfterLast(".")

		val clazz = Class.forName(className).kotlin
		val method = clazz.memberFunctions.firstOrNull { it.name == methodName } ?: throw RuntimeException("cannot find method (expected: $methodName)")
		return method.annotations.firstOrNull { it.annotationClass == ConsistencyCheck::class } as? ConsistencyCheck
			?: method.annotations.firstOrNull { it.annotationClass == ConsistencyBulkCheck::class } as? ConsistencyBulkCheck
	}

	private fun getMappedStatement(invocation: Invocation): MappedStatement {
		return invocation.args[0] as MappedStatement
	}

	private fun getParameterMap(invocation: Invocation): MapperMethod.ParamMap<*> {
		return invocation.args[1] as MapperMethod.ParamMap<*>
	}

}
