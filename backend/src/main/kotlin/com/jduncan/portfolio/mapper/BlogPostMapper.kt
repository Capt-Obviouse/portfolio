package com.jduncan.portfolio.mapper

import com.jduncan.portfolio.dto.BlogPostResponse
import com.jduncan.portfolio.dto.CreateBlogPostRequest
import com.jduncan.portfolio.dto.UpdateBlogPostRequest
import com.jduncan.portfolio.model.BlogPost
import java.time.LocalDateTime

object BlogPostMapper {

  fun BlogPost.toResponse(): BlogPostResponse {
    return BlogPostResponse(
      id = this.id,
      title = this.title,
      content = this.content,
      slug = this.slug,
      author = this.author,
      publishedDate = this.publishedDate,
      lastModifiedDate = this.lastModifiedDate,
      tags = this.tags,
      excerpt = this.excerpt,
      isPublished = this.isPublished,
      readTimeMinutes = this.readTimeMinutes,
      featuredImageUrl = this.featuredImageUrl,
      metaDescription = this.metaDescription
    )
  }

  fun CreateBlogPostRequest.toEntity(): BlogPost {
    return BlogPost.create(
      title = this.title,
      content = this.content,
      author = this.author,
      slug = this.slug ?: generateSlug(this.title),
      tags = this.tags,
      excerpt = this.excerpt,
      isPublished = this.isPublished,
      readTimeMinutes = this.readTimeMinutes,
      featuredImageUrl = this.featuredImageUrl,
      metaDescription = this.metaDescription
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

  fun BlogPost.updateFromRequest(request: UpdateBlogPostRequest): BlogPost {
    return this.copy(
      title = request.title ?: this.title,
      content = request.content ?: this.content,
      author = request.author ?: this.author,
      slug = request.slug ?: this.slug,
      tags = request.tags ?: this.tags,
      excerpt = request.excerpt ?: this.excerpt,
      isPublished = request.isPublished ?: this.isPublished,
      readTimeMinutes = request.readTimeMinutes ?: this.readTimeMinutes,
      featuredImageUrl = request.featuredImageUrl ?: this.featuredImageUrl,
      metaDescription = request.metaDescription ?: this.metaDescription,
      lastModifiedDate = LocalDateTime.now()
    )
  }

  fun List<BlogPost>.toResponseList(): List<BlogPostResponse> {
    return this.map { it.toResponse() }
  }
}
