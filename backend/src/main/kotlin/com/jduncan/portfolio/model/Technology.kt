package com.jduncan.portfolio.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import java.time.LocalDateTime

@Entity
@Table(name = "technologies")
data class Technology(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    @Enumerated(EnumType.STRING)
    val category: TechnologyCategory,
    val description: String? = null,
    val iconUrl: String? = null,
    val proficiencyLevel: Int? = null,
    val yearsOfExperience: Double? = null,
    val isPublished: Boolean = false,
    val publishedDate: LocalDateTime = LocalDateTime.now(),
    val lastModifiedDate: LocalDateTime = LocalDateTime.now()
)
