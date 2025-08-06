package com.jduncan.portfolio.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import com.jduncan.portfolio.model.Role
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.FetchType
import jakarta.persistence.JoinTable

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "username", nullable = false)
    val username: String,
    @Column(name = "email", nullable = false, unique = true)
    val email: String,
    @Column(name = "password", nullable = false)
    val password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
    val roles: MutableSet<Role> = mutableSetOf(),
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime,
    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    val enabled: Boolean
)