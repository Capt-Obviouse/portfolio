package com.jduncan.portfolio.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.jduncan.portfolio.dto.CreateBlogPostRequest
import com.jduncan.portfolio.model.BlogPost
import com.jduncan.portfolio.repo.BlogPostRepository
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@WithMockUser
@TestPropertySource(
  properties =
    [
      "spring.datasource.url=jdbc:h2:mem:testdb",
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.jpa.show-sql=true"
    ]
)
@Transactional
@DisplayName("BlogPost Integration Tests")
class BlogPostIntegrationTest {

  @Autowired private lateinit var webApplicationContext: WebApplicationContext

  @Autowired private lateinit var blogPostRepository: BlogPostRepository

  @Autowired private lateinit var objectMapper: ObjectMapper

  private lateinit var mockMvc: MockMvc

  @BeforeEach
  fun setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    blogPostRepository.deleteAll()
  }

  @Test
  @DisplayName("Should perform full CRUD operations on blog posts")
  fun `should perform full CRUD operations on blog posts`() {
    // Create a blog post
    val createRequest =
      CreateBlogPostRequest(
        title = "Integration Test Post",
        content = "This is a test post for integration testing",
        author = "Integration Test Author",
        isPublished = false
      )

    val createResponse =
      mockMvc
        .perform(
          post("/api/blog-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createRequest))
        )
        .andExpect(status().isCreated)
        .andExpect(jsonPath("$.title").value("Integration Test Post"))
        .andExpect(jsonPath("$.isPublished").value(false))
        .andReturn()

    val createdBlogPost = objectMapper.readTree(createResponse.response.contentAsString)
    val blogPostId = createdBlogPost.get("id").asLong()

    // Verify it exists in database
    val savedBlogPost = blogPostRepository.findById(blogPostId)
    assertThat(savedBlogPost).isPresent
    assertThat(savedBlogPost.get().title).isEqualTo("Integration Test Post")

    // Get the blog post
    mockMvc
      .perform(get("/api/blog-posts/{id}", blogPostId))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.id").value(blogPostId))
      .andExpect(jsonPath("$.title").value("Integration Test Post"))

    // Publish the blog post
    mockMvc
      .perform(put("/api/blog-posts/{id}/publish", blogPostId))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.isPublished").value(true))

    // Verify it appears in published posts
    mockMvc
      .perform(get("/api/blog-posts"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.length()").value(1))
      .andExpect(jsonPath("$[0].id").value(blogPostId))

    // Unpublish the blog post
    mockMvc
      .perform(put("/api/blog-posts/{id}/unpublish", blogPostId))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.isPublished").value(false))

    // Verify it doesn't appear in published posts
    mockMvc
      .perform(get("/api/blog-posts"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.length()").value(0))

    // Delete the blog post
    mockMvc.perform(delete("/api/blog-posts/{id}", blogPostId)).andExpect(status().isNoContent)

    // Verify it's deleted
    mockMvc.perform(get("/api/blog-posts/{id}", blogPostId)).andExpect(status().isNotFound)

    // Verify it's deleted from database
    val deletedBlogPost = blogPostRepository.findById(blogPostId)
    assertThat(deletedBlogPost).isEmpty
  }

  @Test
  @DisplayName("Should filter published vs unpublished posts correctly")
  fun `should filter published vs unpublished posts correctly`() {
    // Create published and unpublished posts
    val publishedPost = blogPostRepository.save(createSampleBlogPost("Published Post", true))
    val unpublishedPost1 =
      blogPostRepository.save(createSampleBlogPost("Unpublished Post 1", false))
    val unpublishedPost2 =
      blogPostRepository.save(createSampleBlogPost("Unpublished Post 2", false))

    // Get published posts
    mockMvc
      .perform(get("/api/blog-posts"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.length()").value(1))
      .andExpect(jsonPath("$[0].title").value("Published Post"))

    // Get unpublished posts
    mockMvc
      .perform(get("/api/blog-posts/unpublished"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.length()").value(2))
  }

  @Test
  @DisplayName("Should validate request data")
  fun `should validate request data`() {
    val invalidRequest =
      CreateBlogPostRequest(
        title = "", // Invalid - blank title
        content = "Valid content",
        author = "Valid Author"
      )

    mockMvc
      .perform(
        post("/api/blog-posts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(invalidRequest))
      )
      .andExpect(status().isBadRequest)

    // Verify no post was created
    assertThat(blogPostRepository.count()).isEqualTo(0)
  }

  private fun createSampleBlogPost(title: String, isPublished: Boolean): BlogPost {
    return BlogPost(
      title = title,
      content = "Sample content for $title",
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
