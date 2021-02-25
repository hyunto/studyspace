package xyz.hyunto.async.config

class DatabaseTypeHolder {
	companion object {
		private val holder = ThreadLocal<DatabaseType>()
		fun get() = holder.get()
		fun set(type: DatabaseType) = holder.set(type)
		fun setMySql1() = holder.set(DatabaseType.MySQL1)
		fun setMySql2() = holder.set(DatabaseType.MySQL2)
		fun clear() = holder.remove()
	}
}
