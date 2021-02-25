package xyz.hyunto.core.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import xyz.hyunto.core.interceptor.DatabaseTypeHolder

@Aspect
@Component
class DualWriteAspect {

	@Around("@within(org.springframework.stereotype.Repository)")
	fun dualWrite(joinPoint: ProceedingJoinPoint): Any? {
		DatabaseTypeHolder.setMySql1()
		val result = joinPoint.proceed()
		DatabaseTypeHolder.setMySql2()
		joinPoint.proceed()
		return result
	}
}
