package com.ztech.grocery.aspect

import com.ztech.grocery.exception.ResourceConflictException
import com.ztech.grocery.exception.ResourceInvalidException
import com.ztech.grocery.exception.ResourceNotFoundException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Component

@Aspect
@Component
class ServiceExceptionAspect {

    @Around("execution(* com.ztech.grocery.service.*.*(..))")
    fun handleServiceExceptions(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: EmptyResultDataAccessException) {
            throw ResourceNotFoundException()
        } catch (e: DataIntegrityViolationException) {
            when {
                e.message!!.contains("DUPLICATE", ignoreCase = true)
                        || e.message!!.contains("UNIQUE", ignoreCase = true) ->
                    throw ResourceConflictException(
                        e.message?.split("unicst_")?.get(1)?.substringBefore("'")
                            ?.run { "Duplicate $this" } ?: ""
                    )
                else -> throw ResourceInvalidException(e.message ?: "")
            }
        }
    }
}