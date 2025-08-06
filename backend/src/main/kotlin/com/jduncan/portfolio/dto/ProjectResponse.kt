package com.jduncan.portfolio.dto

import java.time.LocalDateTime

data class ProjectResponse(
  val id: Long,
  val name: String,
  val description: String,
  val image: String,
  val link: String,
  val githubLink: String,
  val technologies: String,
  val role: String,
  val startDate: String,
  val isPublished: Boolean,
  val publishedDate: LocalDateTime,
  val lastModifiedDate: LocalDateTime
)
