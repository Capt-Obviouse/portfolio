package com.jduncan.portfolio.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateUserRequest(
  @field:NotBlank @field:Size(min = 3, max = 50) val username: String,
  @field:NotBlank @field:Email val email: String,
  @field:NotBlank @field:Size(min = 6) val password: String,
  val roles: List<String> = emptyList(),
  val enabled: Boolean = true
)
