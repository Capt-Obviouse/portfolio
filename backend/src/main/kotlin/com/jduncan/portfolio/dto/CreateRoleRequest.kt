package com.jduncan.portfolio.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateRoleRequest(
  @field:NotBlank
  @field:Pattern(
    regexp = "^ROLE_[A-Z_]+$",
    message = "Role name must start with ROLE_ and contain only uppercase letters and underscores"
  )
  val name: String
)
