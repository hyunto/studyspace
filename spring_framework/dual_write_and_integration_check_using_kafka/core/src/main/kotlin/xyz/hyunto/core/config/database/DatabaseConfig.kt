package xyz.hyunto.core.config.database

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.annotation.MapperScans
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.EnableTransactionManagement
import xyz.hyunto.core.interceptor.DualWriteCheckInterceptor
import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
@MapperScans(
	MapperScan(annotationClass = Repository::class),
	MapperScan(annotationClass = MySql1Mapper::class, basePackages = ["xyz.hyunto"], sqlSessionFactoryRef = "mysql1SessionFactory"),
	MapperScan(annotationClass = MySql2Mapper::class, basePackages = ["xyz.hyunto"], sqlSessionFactoryRef = "mysql2SessionFactory")
)
@EnableTransactionManagement
class DatabaseConfig {

	@Resource(name = "mysql1DataSource")
	private lateinit var mysql1DataSource: HikariDataSource

	@Resource(name = "mysql2DataSource")
	private lateinit var mysql2DataSource: HikariDataSource

	@Bean
	@Primary
	fun mysql1SessionFactory(): SqlSessionFactory {
		return buildSqlSessionFactory(mysql1DataSource)
	}

	@Bean
	fun mysql2SessionFactory(): SqlSessionFactory {
		return buildSqlSessionFactory(mysql2DataSource)
	}


	private fun buildSqlSessionFactory(dataSource: DataSource): SqlSessionFactory {
		val sqlSessionFactoryBean = SqlSessionFactoryBean()
		sqlSessionFactoryBean.setDataSource(dataSource)
//		sqlSessionFactoryBean.setPlugins(ConsistencyCheckInterceptor())
		sqlSessionFactoryBean.setPlugins(DualWriteCheckInterceptor())
		return sqlSessionFactoryBean.`object` ?: throw Exception()
	}
}
