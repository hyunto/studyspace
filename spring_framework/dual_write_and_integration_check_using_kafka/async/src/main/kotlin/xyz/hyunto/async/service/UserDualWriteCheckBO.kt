package xyz.hyunto.async.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.hyunto.core.mapper.UserMapper

@Service
class UserDualWriteCheckBO @Autowired constructor(
) : DualWriteCheckBO() {

}
