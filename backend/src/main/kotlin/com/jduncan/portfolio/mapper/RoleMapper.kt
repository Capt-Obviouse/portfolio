package com.jduncan.portfolio.mapper

import com.jduncan.portfolio.dto.CreateRoleRequest
import com.jduncan.portfolio.dto.RoleResponse
import com.jduncan.portfolio.dto.UpdateRoleRequest
import com.jduncan.portfolio.model.Role

object RoleMapper {

  fun Role.toResponse(): RoleResponse {
    return RoleResponse(id = this.id, name = this.name)
  }

  fun CreateRoleRequest.toEntity(): Role {
    return Role(name = this.name)
  }

  fun Role.updateFromRequest(request: UpdateRoleRequest): Role {
    return this.copy(name = request.name ?: this.name)
  }

  fun List<Role>.toResponseList(): List<RoleResponse> {
    return this.map { it.toResponse() }
  }
}
