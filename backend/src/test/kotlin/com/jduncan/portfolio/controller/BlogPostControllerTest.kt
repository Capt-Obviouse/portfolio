package com.jduncan.portfolio.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jduncan.portfolio.config.TestSecurityConfig
import com.jduncan.portfolio.dto.BlogPostResponse
import com.jduncan.portfolio.dto.CreateBlogPostRequest
import com.jduncan.portfolio.service.BlogPostService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import java.time.LocalDateTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BlogPostController::class)
@Import(TestSecurityConfig::class)
@WithMockUser
@DisplayName("BlogPost Controller Tests")
class BlogPostControllerTest {

  @Autowired private lateinit var mockMvc: MockMvc

  @Autowired private lateinit var objectMapper: ObjectMapper

  @MockkBean private lateinit var blogPostService: BlogPostService

  @Test
  @DisplayName("GET /api/blog-posts should return published blog posts")
  fun `should return published blog posts`() {
    // Given
    val blogPosts =
      listOf(
        createSampleBlogPostResponse(1L, "Post 1", true),
        createSampleBlogPostResponse(2L, "Post 2", true)
      )
    every { blogPostService.getAllPublishedBlogPosts() } returns blogPosts

    // When & Then
    mockMvc
      .perform(get("/api/blog-posts"))
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$").isArray)
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].title").value("Post 1"))
      .andExpect(jsonPath("$[1].title").value("Post 2"))

    verify(exactly = 1) { blogPostService.getAllPublishedBlogPosts() }
  }

  @Test
  @DisplayName("GET /api/blog-posts/{id} should return blog post when exists")
  fun `should return blog post when exists`() {
    // Given
    val blogPostId = 1L
    val blogPost = createSampleBlogPostResponse(blogPostId, "Test Post", true)
    every { blogPostService.getBlogPostById(blogPostId) } returns blogPost

    // When & Then
    mockMvc
      .perform(get("/api/blog-posts/{id}", blogPostId))
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(blogPostId))
      .andExpect(jsonPath("$.title").value("Test Post"))
      .andExpect(jsonPath("$.isPublished").value(true))
  }

  @Test
  @DisplayName("GET /api/blog-posts/{id} should return 404 when blog post not found")
  fun `should return 404 when blog post not found`() {
    // Given
    val blogPostId = 999L
    every { blogPostService.getBlogPostById(blogPostId) } returns null

    // When & Then
    mockMvc.perform(get("/api/blog-posts/{id}", blogPostId)).andExpect(status().isNotFound)
  }

  @Test
  @DisplayName("POST /api/blog-posts should create new blog post")
  fun `should create new blog post`() {
    // Given
    val createRequest =
      CreateBlogPostRequest(
        title = "New Post",
        content = "Post content",
        author = "Test Author",
        isPublished = false
      )

    val createdBlogPost = createSampleBlogPostResponse(1L, "New Post", false)
    every { blogPostService.createBlogPost(any()) } returns createdBlogPost

    // When & Then
    mockMvc
      .perform(
        post("/api/blog-posts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createRequest))
      )
      .andExpect(status().isCreated)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.title").value("New Post"))
      .andExpect(jsonPath("$.author").value("Test Author"))
      .andExpect(jsonPath("$.isPublished").value(false))

    verify(exactly = 1) { blogPostService.createBlogPost(any()) }
  }

  @Test
  @DisplayName("POST /api/blog-posts should return 400 for invalid request")
  fun `should return 400 for invalid request`() {
    // Given - Invalid request with empty title
    val invalidRequest =
      CreateBlogPostRequest(
        title = "", // Invalid - blank title
        content = "Post content",
        author = "Test Author"
      )

    // When & Then
    mockMvc
      .perform(
        post("/api/blog-posts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(invalidRequest))
      )
      .andExpect(status().isBadRequest)

    verify(exactly = 0) { blogPostService.createBlogPost(any()) }
  }

  @Test
  @DisplayName("DELETE /api/blog-posts/{id} should delete blog post when exists")
  fun `should delete blog post when exists`() {
    // Given
    val blogPostId = 1L
    every { blogPostService.deleteBlogPost(blogPostId) } returns true

    // When & Then
    mockMvc.perform(delete("/api/blog-posts/{id}", blogPostId)).andExpect(status().isNoContent)

    verify(exactly = 1) { blogPostService.deleteBlogPost(blogPostId) }
  }

  @Test
  @DisplayName("DELETE /api/blog-posts/{id} should return 404 when blog post not found")
  fun `should return 404 when deleting blog post not found`() {
    // Given
    val blogPostId = 999L
    every { blogPostService.deleteBlogPost(blogPostId) } returns false

    // When & Then
    mockMvc.perform(delete("/api/blog-posts/{id}", blogPostId)).andExpect(status().isNotFound)
  }

  @Test
  @DisplayName("PUT /api/blog-posts/{id}/publish should publish blog post")
  fun `should publish blog post`() {
    // Given
    val blogPostId = 1L
    val publishedBlogPost = createSampleBlogPostResponse(blogPostId, "Test Post", true)
    every { blogPostService.publishBlogPost(blogPostId) } returns publishedBlogPost

    // When & Then
    mockMvc
      .perform(put("/api/blog-posts/{id}/publish", blogPostId))
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.isPublished").value(true))

    verify(exactly = 1) { blogPostService.publishBlogPost(blogPostId) }
  }

  private fun createSampleBlogPostResponse(
    id: Long,
    title: String,
    isPublished: Boolean
  ): BlogPostResponse {
    return BlogPostResponse(
      id = id,
      title = title,
      content = "Sample content",
      slug = title.lowercase().replace(" ", "-"),
      author = "Test Author",
      publishedDate = LocalDateTime.now(),
      lastModifiedDate = LocalDateTime.now(),
      tags = null,
      excerpt = null,
      isPublished = isPublished,
      readTimeMinutes = 5,
      featuredImageUrl = null,
      metaDescription = null
    )
  }
}
