package xyz.hyunto.async.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class RoutingDataSource : AbstractRoutingDataSource() {

	override fun determineCurrentLookupKey(): Any? {
		println("### DatabaseType : ${DatabaseTypeHolder.get()} ###")
		return DatabaseTypeHolder.get()
	}

}
