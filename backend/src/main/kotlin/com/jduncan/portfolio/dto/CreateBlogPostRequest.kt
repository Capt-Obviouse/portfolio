package com.jduncan.portfolio.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateBlogPostRequest(
  @field:NotBlank @field:Size(max = 255) val title: String,
  @field:NotBlank val content: String,
  @field:NotBlank @field:Size(max = 100) val author: String,
  val slug: String? = null,
  val tags: String? = null,
  val excerpt: String? = null,
  val isPublished: Boolean = false,
  val readTimeMinutes: Int? = null,
  val featuredImageUrl: String? = null,
  val metaDescription: String? = null
)
