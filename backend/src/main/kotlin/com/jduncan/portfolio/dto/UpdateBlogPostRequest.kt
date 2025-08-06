package com.jduncan.portfolio.dto

data class UpdateBlogPostRequest(
  val title: String? = null,
  val content: String? = null,
  val author: String? = null,
  val slug: String? = null,
  val tags: String? = null,
  val excerpt: String? = null,
  val isPublished: Boolean? = null,
  val readTimeMinutes: Int? = null,
  val featuredImageUrl: String? = null,
  val metaDescription: String? = null
)
