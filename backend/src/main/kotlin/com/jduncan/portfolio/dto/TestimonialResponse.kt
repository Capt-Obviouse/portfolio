package com.jduncan.portfolio.dto

import java.time.LocalDateTime

data class TestimonialResponse(
  val id: Long,
  val name: String,
  val title: String,
  val company: String,
  val content: String,
  val imageUrl: String?,
  val isPublished: Boolean,
  val testimonialDate: LocalDateTime,
  val lastModifiedDate: LocalDateTime
)
