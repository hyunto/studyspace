package xyz.hyunto.core.config.database

import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.annotation.PostConstruct
import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
@ConditionalOnExpression(value = "\${flyway.migrate.enabled:true}")
class FlywayConfig{

	@Resource(name = "mysql1DataSource")
	private lateinit var mysql1DataSource: HikariDataSource

	@Resource(name = "mysql2DataSource")
	private lateinit var mysql2DataSource: HikariDataSource

	@PostConstruct
	fun migrateFlyway() {
		buildFlywayConfig(mysql1DataSource).migrate()
		buildFlywayConfig(mysql2DataSource).migrate()
	}

	private fun buildFlywayConfig(dataSource: DataSource): Flyway {
		return Flyway.configure()
			.dataSource(dataSource)
			.load()
	}
}
