package com.jduncan.portfolio.startup

import com.jduncan.portfolio.model.Role
import com.jduncan.portfolio.model.User
import com.jduncan.portfolio.repo.RoleRepository
import com.jduncan.portfolio.repo.UserRepository
import java.time.LocalDateTime
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataLoader(
  private val roleRepository: RoleRepository,
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

  val userRole =
    roleRepository.findByName("USER").orElseGet { roleRepository.save(Role(name = "USER")) }
  val adminRole =
    roleRepository.findByName("ADMIN").orElseGet { roleRepository.save(Role(name = "ADMIN")) }

  override fun run(args: ApplicationArguments?) {
    if (userRepository.findByUsername("admin").isEmpty()) {
      userRepository.save(
        User(
          id = 0,
          username = "admin",
          password = passwordEncoder.encode("password"),
          email = "admin@example.com",
          roles = mutableSetOf(userRole, adminRole),
          createdAt = LocalDateTime.now(),
          updatedAt = LocalDateTime.now(),
          enabled = true
        )
      )
    }
  }
}
