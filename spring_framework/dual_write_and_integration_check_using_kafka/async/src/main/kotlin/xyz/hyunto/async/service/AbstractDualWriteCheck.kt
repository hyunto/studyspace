package xyz.hyunto.async.service

import xyz.hyunto.async.config.DatabaseType
import xyz.hyunto.async.config.DatabaseTypeHolder
import kotlin.reflect.KCallable

abstract class AbstractDualWriteCheck {

	fun execute(type: DatabaseType, queryName: String, params: List<Any>): Any? {
		DatabaseTypeHolder.set(type)
		return select(queryName, params)
	}

	protected fun getMethod(mapper: Any, name: String): KCallable<*> {
		return mapper::class.members.single { it.name == name } ?: throw RuntimeException("$mapper not exist")
	}

	abstract fun select(queryName: String, params: List<Any>): Any?

}
