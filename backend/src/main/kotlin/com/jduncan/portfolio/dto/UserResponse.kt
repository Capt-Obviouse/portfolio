package com.jduncan.portfolio.dto

data class UserResponse(
  val id: Long,
  val username: String,
  val email: String,
  val roles: List<String>,
  val enabled: Boolean
)
