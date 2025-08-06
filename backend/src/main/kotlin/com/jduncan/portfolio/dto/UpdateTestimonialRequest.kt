package com.jduncan.portfolio.dto

data class UpdateTestimonialRequest(
  val name: String? = null,
  val title: String? = null,
  val company: String? = null,
  val content: String? = null,
  val imageUrl: String? = null,
  val isPublished: Boolean? = null
)
