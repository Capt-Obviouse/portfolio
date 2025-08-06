package com.jduncan.portfolio.dto

data class UpdateProjectRequest(
  val name: String? = null,
  val description: String? = null,
  val image: String? = null,
  val link: String? = null,
  val githubLink: String? = null,
  val technologies: String? = null,
  val role: String? = null,
  val startDate: String? = null,
  val isPublished: Boolean? = null
)
