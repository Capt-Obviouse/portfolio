package com.jduncan.portfolio.mapper

import com.jduncan.portfolio.dto.CreateUserRequest
import com.jduncan.portfolio.dto.UpdateUserRequest
import com.jduncan.portfolio.dto.UserResponse
import com.jduncan.portfolio.model.Role
import com.jduncan.portfolio.model.User

object UserMapper {

  fun User.toResponse(): UserResponse {
    return UserResponse(
      id = this.id,
      username = this.username,
      email = this.email,
      roles = this.roles.map { it.name },
      enabled = this.enabled
    )
  }

  fun CreateUserRequest.toEntity(roles: Set<Role> = emptySet()): User {
    return User(
      username = this.username,
      email = this.email,
      password = this.password, // Note: Password should be encoded before calling this
      roles = roles.toMutableSet(),
      enabled = this.enabled,
      createdAt = java.time.LocalDateTime.now(),
      updatedAt = java.time.LocalDateTime.now()
    )
  }

  fun User.updateFromRequest(request: UpdateUserRequest, roles: Set<Role>? = null): User {
    return this.copy(
      username = request.username ?: this.username,
      email = request.email ?: this.email,
      password = request.password
          ?: this.password, // Note: Password should be encoded before calling this
      roles = roles?.toMutableSet() ?: this.roles,
      enabled = request.enabled ?: this.enabled,
      updatedAt = java.time.LocalDateTime.now()
    )
  }

  fun List<User>.toResponseList(): List<UserResponse> {
    return this.map { it.toResponse() }
  }
}
