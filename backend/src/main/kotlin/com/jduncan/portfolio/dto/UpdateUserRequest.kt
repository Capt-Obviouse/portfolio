package com.jduncan.portfolio.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
  @field:Size(min = 3, max = 50) val username: String? = null,
  @field:Email val email: String? = null,
  @field:Size(min = 6) val password: String? = null,
  val roles: List<String>? = null,
  val enabled: Boolean? = null
)
