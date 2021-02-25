package xyz.hyunto.core.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Aspect
@Component
@Deprecated("MyBatis 인터셉터로 구현")
class DualWriteCheckAspect {

//	@Around("@annotation(xyz.hyunto.core.interceptor.annotations.DualWriteCheck)")
//	@Around("execution(** org.apache.ibatis.session.SqlSession.insert(String, ..))")
	fun afterCommit(joinPoint: ProceedingJoinPoint) {
		println("### DualWriteCheckAspect ###")
		println(joinPoint)
		val result = joinPoint.proceed()
		print(result)
//		throw RuntimeException("kkk")
	}
}
