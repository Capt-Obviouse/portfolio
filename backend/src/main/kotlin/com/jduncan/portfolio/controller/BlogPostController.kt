package com.jduncan.portfolio.controller

import com.jduncan.portfolio.dto.BlogPostResponse
import com.jduncan.portfolio.dto.CreateBlogPostRequest
import com.jduncan.portfolio.dto.UpdateBlogPostRequest
import com.jduncan.portfolio.service.BlogPostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/blog-posts")
class BlogPostController(private val blogPostService: BlogPostService) {

  @GetMapping
  fun getPublishedBlogPosts(): List<BlogPostResponse> {
    return blogPostService.getAllPublishedBlogPosts()
  }

  @GetMapping("/unpublished")
  fun getUnpublishedBlogPosts(): List<BlogPostResponse> {
    return blogPostService.getAllUnpublishedBlogPosts()
  }

  @GetMapping("/{id}")
  fun getBlogPost(@PathVariable id: Long): ResponseEntity<BlogPostResponse> {
    val blogPost = blogPostService.getBlogPostById(id)
    return if (blogPost != null) {
      ResponseEntity.ok(blogPost)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PostMapping
  fun createBlogPost(
    @Valid @RequestBody request: CreateBlogPostRequest
  ): ResponseEntity<BlogPostResponse> {
    val createdBlogPost = blogPostService.createBlogPost(request)
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBlogPost)
  }

  @PutMapping("/{id}")
  fun updateBlogPost(
    @PathVariable id: Long,
    @Valid @RequestBody request: UpdateBlogPostRequest
  ): ResponseEntity<BlogPostResponse> {
    val updatedBlogPost = blogPostService.updateBlogPost(id, request)
    return if (updatedBlogPost != null) {
      ResponseEntity.ok(updatedBlogPost)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping("/{id}")
  fun deleteBlogPost(@PathVariable id: Long): ResponseEntity<Void> {
    val deleted = blogPostService.deleteBlogPost(id)
    return if (deleted) {
      ResponseEntity.noContent().build()
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/publish")
  fun publishBlogPost(@PathVariable id: Long): ResponseEntity<BlogPostResponse> {
    val publishedBlogPost = blogPostService.publishBlogPost(id)
    return if (publishedBlogPost != null) {
      ResponseEntity.ok(publishedBlogPost)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/unpublish")
  fun unpublishBlogPost(@PathVariable id: Long): ResponseEntity<BlogPostResponse> {
    val unpublishedBlogPost = blogPostService.unpublishBlogPost(id)
    return if (unpublishedBlogPost != null) {
      ResponseEntity.ok(unpublishedBlogPost)
    } else {
      ResponseEntity.notFound().build()
    }
  }
}
