package com.jduncan.portfolio.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import com.jduncan.portfolio.model.BlogPost
import com.jduncan.portfolio.repo.BlogPostRepository

@RestController
@RequestMapping("/api/blog-posts")
class BlogPostController(
    private val blogPostRepository: BlogPostRepository
) {

    @GetMapping
    fun getPublishedBlogPosts(): List<BlogPost> {
        return blogPostRepository.findByIsPublishedTrueOrderByPublishedDateDesc()
    }

    @GetMapping("/unpublished")
    fun getUnpublishedBlogPosts(): List<BlogPost> {
        return blogPostRepository.findByIsPublishedFalseOrderByPublishedDateDesc()
    }

    @GetMapping("/{id}")
    fun getBlogPost(@PathVariable id: Long): ResponseEntity<BlogPost> {
        val blogPost = blogPostRepository.findById(id).orElse(null)
        return if (blogPost != null) {
            ResponseEntity.ok(blogPost)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createBlogPost(@RequestBody blogPost: BlogPost): BlogPost {
        return blogPostRepository.save(blogPost)
    }

    @PutMapping("/{id}")
    fun updateBlogPost(@PathVariable id: Long, @RequestBody blogPost: BlogPost): ResponseEntity<BlogPost> {
        val existingBlogPost = blogPostRepository.findById(id).orElse(null)
        return if (existingBlogPost != null) {
            val updatedBlogPost = blogPostRepository.save(blogPost.copy(id = id))
            ResponseEntity.ok(updatedBlogPost)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBlogPost(@PathVariable id: Long): ResponseEntity<Void> {
        val blogPost = blogPostRepository.findById(id).orElse(null)
        return if (blogPost != null) {
            blogPostRepository.delete(blogPost)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
