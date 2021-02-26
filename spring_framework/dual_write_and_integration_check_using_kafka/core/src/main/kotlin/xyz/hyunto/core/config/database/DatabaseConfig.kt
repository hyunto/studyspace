package xyz.hyunto.core.config.database

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.annotation.MapperScans
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.EnableTransactionManagement
import xyz.hyunto.core.interceptor.DatabaseType
import xyz.hyunto.core.interceptor.DualWriteCheckMessage
import xyz.hyunto.core.interceptor.DualWriteInterceptor
import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
@MapperScans(
	MapperScan(annotationClass = Repository::class, basePackages = ["xyz.hyunto.core.mapper"]),
)
@EnableTransactionManagement
class DatabaseConfig {

	@Resource(name = "mysql1DataSource")
	private lateinit var mysql1DataSource: HikariDataSource

	@Resource(name = "mysql2DataSource")
	private lateinit var mysql2DataSource: HikariDataSource

	@Resource(name = "dualWriteCheckKafkaTemplate")
	private lateinit var dualWriteCheckKafkaTemplate: KafkaTemplate<String, DualWriteCheckMessage>

	@Autowired
	private lateinit var kafkaTemplate: KafkaTemplate<String, String>
	//
	//	@Bean
	//	@Primary
	//	fun mysql1SessionFactory(): SqlSessionFactory {
	//		return buildSqlSessionFactory(mysql1DataSource)
	//	}
	//
	//	@Bean
	//	fun mysql2SessionFactory(): SqlSessionFactory {
	//		return buildSqlSessionFactory(mysql2DataSource)
	//	}
	//
	//	private fun buildSqlSessionFactory(dataSource: DataSource): SqlSessionFactory {
	//		val sqlSessionFactoryBean = SqlSessionFactoryBean()
	//		sqlSessionFactoryBean.setDataSource(dataSource)
	////		sqlSessionFactoryBean.setPlugins(ConsistencyCheckInterceptor())
	//		sqlSessionFactoryBean.setPlugins(dualWriteCheckInterceptor())
	//		return sqlSessionFactoryBean.`object` ?: throw Exception()
	//	}

	@Bean
	fun dualWriteCheckInterceptor(): DualWriteInterceptor {
		return DualWriteInterceptor(kafkaTemplate)
	}

	@Bean
	fun routingDataSource(): DataSource {
		val routingDataSource: AbstractRoutingDataSource = RoutingDataSource()
		routingDataSource.setTargetDataSources(mapOf(
			DatabaseType.MySQL1 to mysql1DataSource,
			DatabaseType.MySQL2 to mysql2DataSource
		))
		return routingDataSource
	}

	@Bean
	fun sqlSessionFactory(): SqlSessionFactory {
		val sqlSessionFactoryBean = SqlSessionFactoryBean()
		sqlSessionFactoryBean.setDataSource(routingDataSource())
		sqlSessionFactoryBean.setPlugins(dualWriteCheckInterceptor())
		return sqlSessionFactoryBean.`object` ?: throw Exception()
	}

}
