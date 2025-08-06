package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.Role
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
  fun findByName(name: String): Optional<Role>
}
