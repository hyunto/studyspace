package xyz.hyunto.core.config.database

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableConfigurationProperties
class DataSourceProperties {

	@Bean(name = ["mysql1DataSource"])
	@Qualifier("mysql1DataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari.mysql1")
	fun mysql1DataSource(): HikariDataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

	@Bean(name = ["mysql2DataSource"])
	@Qualifier("mysql2DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.mysql2")
	fun mysql2DataSource(): HikariDataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()
}
