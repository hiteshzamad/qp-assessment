package com.ztech.grocery.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [MobileValidator::class])
annotation class ValidMobile(
    val message: String = "Invalid mobile",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class MobileValidator : ConstraintValidator<ValidMobile, String> {
    override fun isValid(name: String?, context: ConstraintValidatorContext?): Boolean {
        return name != null && name.length in 10..15 && name.matches(Regex("^[+0-9]+$"))
    }
}
