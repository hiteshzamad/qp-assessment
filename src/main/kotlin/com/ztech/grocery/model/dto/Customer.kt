package com.ztech.grocery.model.dto

import com.ztech.grocery.validator.ValidMobile
import com.ztech.grocery.validator.ValidName

data class CustomerCreateRequest(
    @field:ValidName
    val name: String,
    @field:ValidMobile
    val mobile: String
)
