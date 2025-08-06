package com.jduncan.portfolio.startup

import org.springframework.stereotype.Component
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.ApplicationArguments
import org.springframework.security.crypto.password.PasswordEncoder
import com.jduncan.portfolio.model.Role
import com.jduncan.portfolio.model.User
import com.jduncan.portfolio.repo.UserRepository
import com.jduncan.portfolio.repo.RoleRepository
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime


@Component
class DataLoader(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    val userRole = roleRepository.findByName("USER").orElseGet { roleRepository.save(Role(name = "USER")) }
    val adminRole = roleRepository.findByName("ADMIN").orElseGet { roleRepository.save(Role(name = "ADMIN")) }

    override fun run(args: ApplicationArguments?) {
        if(userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(User(
                id = 0,
                username = "admin",
                password = passwordEncoder.encode("password"),
                email = "admin@example.com",
                roles = mutableSetOf(userRole, adminRole),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                enabled = true
            ))
        }
    }

}