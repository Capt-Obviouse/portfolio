package com.jduncan.portfolio.controller

import com.jduncan.portfolio.dto.CreateTestimonialRequest
import com.jduncan.portfolio.dto.TestimonialResponse
import com.jduncan.portfolio.dto.UpdateTestimonialRequest
import com.jduncan.portfolio.service.TestimonialService
import io.swagger.v3.oas.annotations.Operation
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
@RequestMapping("/api/testimonials")
class TestimonialController(private val testimonialService: TestimonialService) {

  @GetMapping
  @Operation(summary = "Get all published testimonials")
  fun getPublishedTestimonials(): List<TestimonialResponse> {
    return testimonialService.getAllPublishedTestimonials()
  }

  @GetMapping("/unpublished")
  @Operation(summary = "Get all unpublished testimonials")
  fun getUnpublishedTestimonials(): List<TestimonialResponse> {
    return testimonialService.getAllUnpublishedTestimonials()
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a testimonial by ID")
  fun getTestimonial(@PathVariable id: Long): ResponseEntity<TestimonialResponse> {
    val testimonial = testimonialService.getTestimonialById(id)
    return if (testimonial != null) {
      ResponseEntity.ok(testimonial)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PostMapping
  @Operation(summary = "Create a new testimonial")
  fun createTestimonial(
    @Valid @RequestBody request: CreateTestimonialRequest
  ): ResponseEntity<TestimonialResponse> {
    val createdTestimonial = testimonialService.createTestimonial(request)
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial)
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a testimonial")
  fun updateTestimonial(
    @PathVariable id: Long,
    @Valid @RequestBody request: UpdateTestimonialRequest
  ): ResponseEntity<TestimonialResponse> {
    val updatedTestimonial = testimonialService.updateTestimonial(id, request)
    return if (updatedTestimonial != null) {
      ResponseEntity.ok(updatedTestimonial)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a testimonial")
  fun deleteTestimonial(@PathVariable id: Long): ResponseEntity<Void> {
    val deleted = testimonialService.deleteTestimonial(id)
    return if (deleted) {
      ResponseEntity.noContent().build()
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/publish")
  @Operation(summary = "Publish a testimonial")
  fun publishTestimonial(@PathVariable id: Long): ResponseEntity<TestimonialResponse> {
    val publishedTestimonial = testimonialService.publishTestimonial(id)
    return if (publishedTestimonial != null) {
      ResponseEntity.ok(publishedTestimonial)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/unpublish")
  @Operation(summary = "Unpublish a testimonial")
  fun unpublishTestimonial(@PathVariable id: Long): ResponseEntity<TestimonialResponse> {
    val unpublishedTestimonial = testimonialService.unpublishTestimonial(id)
    return if (unpublishedTestimonial != null) {
      ResponseEntity.ok(unpublishedTestimonial)
    } else {
      ResponseEntity.notFound().build()
    }
  }
}
