package com.jduncan.portfolio.service

import com.jduncan.portfolio.dto.BlogPostResponse
import com.jduncan.portfolio.dto.CreateBlogPostRequest
import com.jduncan.portfolio.dto.UpdateBlogPostRequest
import com.jduncan.portfolio.mapper.BlogPostMapper.toEntity
import com.jduncan.portfolio.mapper.BlogPostMapper.toResponse
import com.jduncan.portfolio.mapper.BlogPostMapper.toResponseList
import com.jduncan.portfolio.mapper.BlogPostMapper.updateFromRequest
import com.jduncan.portfolio.repo.BlogPostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlogPostService(private val blogPostRepository: BlogPostRepository) {

  @Transactional(readOnly = true)
  fun getAllPublishedBlogPosts(): List<BlogPostResponse> {
    return blogPostRepository.findByIsPublishedTrueOrderByPublishedDateDesc().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getAllUnpublishedBlogPosts(): List<BlogPostResponse> {
    return blogPostRepository.findByIsPublishedFalseOrderByPublishedDateDesc().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getBlogPostById(id: Long): BlogPostResponse? {
    return blogPostRepository.findById(id).orElse(null)?.toResponse()
  }

  fun createBlogPost(request: CreateBlogPostRequest): BlogPostResponse {
    val entity = request.toEntity()
    val savedEntity = blogPostRepository.save(entity)
    return savedEntity.toResponse()
  }

  fun updateBlogPost(id: Long, request: UpdateBlogPostRequest): BlogPostResponse? {
    val existingBlogPost = blogPostRepository.findById(id).orElse(null)
    return existingBlogPost?.let {
      val updatedEntity = it.updateFromRequest(request)
      val savedEntity = blogPostRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun deleteBlogPost(id: Long): Boolean {
    return if (blogPostRepository.existsById(id)) {
      blogPostRepository.deleteById(id)
      true
    } else {
      false
    }
  }

  fun publishBlogPost(id: Long): BlogPostResponse? {
    val existingBlogPost = blogPostRepository.findById(id).orElse(null)
    return existingBlogPost?.let {
      val updatedEntity =
        it.copy(isPublished = true, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = blogPostRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun unpublishBlogPost(id: Long): BlogPostResponse? {
    val existingBlogPost = blogPostRepository.findById(id).orElse(null)
    return existingBlogPost?.let {
      val updatedEntity =
        it.copy(isPublished = false, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = blogPostRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }
}
