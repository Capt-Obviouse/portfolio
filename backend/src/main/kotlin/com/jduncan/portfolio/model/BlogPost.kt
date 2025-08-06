package com.jduncan.portfolio.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "blog_posts")
data class BlogPost(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
  val title: String,
  @Column(columnDefinition = "TEXT") val content: String,
  @Column(unique = true) val slug: String = "",
  val author: String,
  val publishedDate: LocalDateTime = LocalDateTime.now(),
  val lastModifiedDate: LocalDateTime = LocalDateTime.now(),
  val tags: String? = null,
  @Column(columnDefinition = "TEXT") val excerpt: String? = null,
  val isPublished: Boolean = false,
  val readTimeMinutes: Int? = null,
  val featuredImageUrl: String? = null,
  @Column(columnDefinition = "TEXT") val metaDescription: String? = null
) {
  companion object {
    fun create(
      title: String,
      content: String,
      author: String,
      slug: String = generateSlug(title),
      publishedDate: LocalDateTime = LocalDateTime.now(),
      lastModifiedDate: LocalDateTime = LocalDateTime.now(),
      tags: String? = null,
      excerpt: String? = null,
      isPublished: Boolean = false,
      readTimeMinutes: Int? = null,
      featuredImageUrl: String? = null,
      metaDescription: String? = null
    ): BlogPost {
      return BlogPost(
        title = title,
        content = content,
        slug = slug,
        author = author,
        publishedDate = publishedDate,
        lastModifiedDate = lastModifiedDate,
        tags = tags,
        excerpt = excerpt,
        isPublished = isPublished,
        readTimeMinutes = readTimeMinutes,
        featuredImageUrl = featuredImageUrl,
        metaDescription = metaDescription
      )
    }

    private fun generateSlug(title: String): String {
      return title
        .lowercase()
        .replace(Regex("[^a-z0-9\\s-]"), "")
        .replace(Regex("\\s+"), "-")
        .replace(Regex("-+"), "-")
        .trim('-')
    }
  }
}
