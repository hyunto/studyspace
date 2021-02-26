package xyz.hyunto.async.config

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.annotation.MapperScans
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
@MapperScans(
	MapperScan(annotationClass = Repository::class, basePackages = ["xyz.hyunto.async.mapper"]),
)
@EnableTransactionManagement
class DatabaseConfig {

	@Resource(name = "mysql1DataSource")
	private lateinit var mysql1DataSource: HikariDataSource

	@Resource(name = "mysql2DataSource")
	private lateinit var mysql2DataSource: HikariDataSource

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
		return sqlSessionFactoryBean.`object` ?: throw Exception()
	}

}
