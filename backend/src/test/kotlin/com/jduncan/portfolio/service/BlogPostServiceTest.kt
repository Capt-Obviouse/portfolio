package com.jduncan.portfolio.service

import com.jduncan.portfolio.dto.CreateBlogPostRequest
import com.jduncan.portfolio.dto.UpdateBlogPostRequest
import com.jduncan.portfolio.model.BlogPost
import com.jduncan.portfolio.repo.BlogPostRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDateTime
import java.util.Optional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BlogPost Service Tests")
class BlogPostServiceTest {

  private lateinit var blogPostRepository: BlogPostRepository
  private lateinit var blogPostService: BlogPostService

  @BeforeEach
  fun setup() {
    blogPostRepository = mockk()
    blogPostService = BlogPostService(blogPostRepository)
  }

  @Test
  @DisplayName("Should get all published blog posts")
  fun `should get all published blog posts`() {
    // Given
    val publishedBlogPosts =
      listOf(createSampleBlogPost(1L, "Post 1", true), createSampleBlogPost(2L, "Post 2", true))
    every { blogPostRepository.findByIsPublishedTrueOrderByPublishedDateDesc() } returns
      publishedBlogPosts

    // When
    val result = blogPostService.getAllPublishedBlogPosts()

    // Then
    assertThat(result).hasSize(2)
    assertThat(result[0].title).isEqualTo("Post 1")
    assertThat(result[1].title).isEqualTo("Post 2")
    verify(exactly = 1) { blogPostRepository.findByIsPublishedTrueOrderByPublishedDateDesc() }
  }

  @Test
  @DisplayName("Should get blog post by ID when exists")
  fun `should get blog post by ID when exists`() {
    // Given
    val blogPostId = 1L
    val blogPost = createSampleBlogPost(blogPostId, "Test Post", true)
    every { blogPostRepository.findById(blogPostId) } returns Optional.of(blogPost)

    // When
    val result = blogPostService.getBlogPostById(blogPostId)

    // Then
    assertThat(result).isNotNull
    assertThat(result?.title).isEqualTo("Test Post")
    assertThat(result?.id).isEqualTo(blogPostId)
  }

  @Test
  @DisplayName("Should return null when blog post not found")
  fun `should return null when blog post not found`() {
    // Given
    val blogPostId = 999L
    every { blogPostRepository.findById(blogPostId) } returns Optional.empty()

    // When
    val result = blogPostService.getBlogPostById(blogPostId)

    // Then
    assertThat(result).isNull()
  }

  @Test
  @DisplayName("Should create new blog post")
  fun `should create new blog post`() {
    // Given
    val request =
      CreateBlogPostRequest(
        title = "New Post",
        content = "Post content",
        author = "Test Author",
        isPublished = false
      )

    val savedBlogPost = createSampleBlogPost(1L, "New Post", false)
    val capturedBlogPost = slot<BlogPost>()

    every { blogPostRepository.save(capture(capturedBlogPost)) } returns savedBlogPost

    // When
    val result = blogPostService.createBlogPost(request)

    // Then
    assertThat(result.title).isEqualTo("New Post")
    assertThat(result.author).isEqualTo("Test Author")
    assertThat(result.isPublished).isFalse()

    // Verify the captured blog post has correct values
    assertThat(capturedBlogPost.captured.title).isEqualTo("New Post")
    assertThat(capturedBlogPost.captured.content).isEqualTo("Post content")
  }

  @Test
  @DisplayName("Should update existing blog post")
  fun `should update existing blog post`() {
    // Given
    val blogPostId = 1L
    val existingBlogPost = createSampleBlogPost(blogPostId, "Old Title", false)
    val updateRequest = UpdateBlogPostRequest(title = "Updated Title", isPublished = true)

    val updatedBlogPost =
      existingBlogPost.copy(
        title = "Updated Title",
        isPublished = true,
        lastModifiedDate = LocalDateTime.now()
      )

    every { blogPostRepository.findById(blogPostId) } returns Optional.of(existingBlogPost)
    every { blogPostRepository.save(any()) } returns updatedBlogPost

    // When
    val result = blogPostService.updateBlogPost(blogPostId, updateRequest)

    // Then
    assertThat(result).isNotNull
    assertThat(result?.title).isEqualTo("Updated Title")
    assertThat(result?.isPublished).isTrue()
  }

  @Test
  @DisplayName("Should delete blog post when exists")
  fun `should delete blog post when exists`() {
    // Given
    val blogPostId = 1L
    every { blogPostRepository.existsById(blogPostId) } returns true
    every { blogPostRepository.deleteById(blogPostId) } returns Unit

    // When
    val result = blogPostService.deleteBlogPost(blogPostId)

    // Then
    assertThat(result).isTrue()
    verify(exactly = 1) { blogPostRepository.deleteById(blogPostId) }
  }

  @Test
  @DisplayName("Should return false when deleting non-existent blog post")
  fun `should return false when deleting non-existent blog post`() {
    // Given
    val blogPostId = 999L
    every { blogPostRepository.existsById(blogPostId) } returns false

    // When
    val result = blogPostService.deleteBlogPost(blogPostId)

    // Then
    assertThat(result).isFalse()
    verify(exactly = 0) { blogPostRepository.deleteById(any()) }
  }

  @Test
  @DisplayName("Should publish blog post")
  fun `should publish blog post`() {
    // Given
    val blogPostId = 1L
    val unpublishedPost = createSampleBlogPost(blogPostId, "Test Post", false)
    val publishedPost = unpublishedPost.copy(isPublished = true)

    every { blogPostRepository.findById(blogPostId) } returns Optional.of(unpublishedPost)
    every { blogPostRepository.save(any()) } returns publishedPost

    // When
    val result = blogPostService.publishBlogPost(blogPostId)

    // Then
    assertThat(result).isNotNull
    assertThat(result?.isPublished).isTrue()
  }

  private fun createSampleBlogPost(id: Long, title: String, isPublished: Boolean): BlogPost {
    return BlogPost(
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
