package com.jduncan.portfolio.service

import com.jduncan.portfolio.model.User
import com.jduncan.portfolio.repo.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    val user =
      userRepository.findByUsername(username).orElseThrow {
        UsernameNotFoundException("User '$username' not found")
      }

    val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }

    return org.springframework.security.core.userdetails.User(
      user.username,
      user.password,
      true, // enabled
      true, // accountNonExpired
      true, // credentialsNonExpired
      true, // accountNonLocked
      authorities
    )
  }
}
