package com.jduncan.portfolio.dto

import java.time.LocalDateTime

data class BlogPostResponse(
  val id: Long,
  val title: String,
  val content: String,
  val slug: String,
  val author: String,
  val publishedDate: LocalDateTime,
  val lastModifiedDate: LocalDateTime,
  val tags: String?,
  val excerpt: String?,
  val isPublished: Boolean,
  val readTimeMinutes: Int?,
  val featuredImageUrl: String?,
  val metaDescription: String?
)
