package com.ztech.grocery.model.dto

import com.ztech.grocery.validator.ValidPassword
import com.ztech.grocery.validator.ValidUsername

data class AccountCreateRequest(
    @field:ValidUsername
    val username: String,
    @field:ValidPassword
    val password: String
)