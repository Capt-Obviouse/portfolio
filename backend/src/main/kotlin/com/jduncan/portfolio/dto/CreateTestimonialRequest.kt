package com.jduncan.portfolio.dto

import jakarta.validation.constraints.NotBlank

data class CreateTestimonialRequest(
  @field:NotBlank val name: String,
  @field:NotBlank val title: String,
  @field:NotBlank val company: String,
  @field:NotBlank val content: String,
  val imageUrl: String? = null,
  val isPublished: Boolean = false
)
