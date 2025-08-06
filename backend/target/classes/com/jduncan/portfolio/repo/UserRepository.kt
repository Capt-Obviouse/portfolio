package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.User
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
  fun findByEmail(email: String): Optional<User>

  fun findByUsername(username: String): Optional<User>
}
