package com.ztech.grocery.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [PasswordValidator::class])
annotation class ValidPassword(
    val message: String = "Invalid password min 8 characters max 64 characters and should contain at least one lowercase, one uppercase, one digit and one special character",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class PasswordValidator : ConstraintValidator<ValidPassword, String> {
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean {
        return password != null && password.length in 8..64
                && password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,64}"))
    }
}
