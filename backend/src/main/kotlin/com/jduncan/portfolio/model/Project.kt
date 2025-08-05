package com.jduncan.portfolio.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.ManyToOne
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn

@Entity
@Table(name = "projects")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
    val link: String,
    val githubLink: String,
    val technologies: String,
    val role: String,
    val startDate: String,
)