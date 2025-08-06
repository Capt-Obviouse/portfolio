package com.jduncan.portfolio.dto

import jakarta.validation.constraints.NotBlank

data class CreateProjectRequest(
  @field:NotBlank val name: String,
  @field:NotBlank val description: String,
  @field:NotBlank val image: String,
  @field:NotBlank val link: String,
  @field:NotBlank val githubLink: String,
  @field:NotBlank val technologies: String,
  @field:NotBlank val role: String,
  @field:NotBlank val startDate: String,
  val isPublished: Boolean = false
)
