package xyz.hyunto.async.service

import org.springframework.stereotype.Service
import xyz.hyunto.async.listener.enums.TableName

@Service
class DualWriteCheckBO {

	private val dualWriteCheckMap: Map<TableName, AbstractDualWriteCheck> = mutableMapOf()


}
