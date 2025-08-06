package com.jduncan.portfolio.dto

import jakarta.validation.constraints.Pattern

data class UpdateRoleRequest(
  @field:Pattern(
    regexp = "^ROLE_[A-Z_]+$",
    message = "Role name must start with ROLE_ and contain only uppercase letters and underscores"
  )
  val name: String? = null
)
