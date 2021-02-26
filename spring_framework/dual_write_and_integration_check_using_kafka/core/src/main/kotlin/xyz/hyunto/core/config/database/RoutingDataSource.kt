package xyz.hyunto.core.config.database

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import xyz.hyunto.core.interceptor.DatabaseTypeHolder

class RoutingDataSource : AbstractRoutingDataSource() {

	override fun determineCurrentLookupKey(): Any? {
		return DatabaseTypeHolder.get()
	}

}
