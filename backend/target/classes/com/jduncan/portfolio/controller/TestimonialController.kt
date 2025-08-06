package com.jduncan.portfolio.controller

import com.jduncan.portfolio.model.Testimonial
import com.jduncan.portfolio.repo.TestimonialRepository
import io.swagger.v3.oas.annotations.Operation
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
class TestimonialController(private val testimonialRepository: TestimonialRepository) {

  @GetMapping("/")
  @Operation(summary = "Get all published testimonials")
  fun getPublishedTestimonials(): List<Testimonial> {
    return testimonialRepository.findByIsPublishedTrueOrderByTestimonialDateDesc()
  }

  @GetMapping("/unpublished")
  @Operation(summary = "Get all unpublished testimonials")
  fun getUnpublishedTestimonials(): List<Testimonial> {
    return testimonialRepository.findByIsPublishedFalseOrderByTestimonialDateDesc()
  }

  @PostMapping
  @Operation(summary = "Create a new testimonial")
  fun createTestimonial(@RequestBody testimonial: Testimonial): Testimonial {
    return testimonialRepository.save(testimonial)
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a testimonial")
  fun updateTestimonial(
    @PathVariable id: Long,
    @RequestBody testimonial: Testimonial
  ): Testimonial {
    val existingTestimonial = testimonialRepository.findById(id).orElse(null)
    if (existingTestimonial != null) {
      return testimonialRepository.save(testimonial.copy(id = id))
    }
    throw RuntimeException("Testimonial not found")
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a testimonial")
  fun deleteTestimonial(@PathVariable id: Long): ResponseEntity<Void> {
    val testimonial = testimonialRepository.findById(id).orElse(null)
    if (testimonial != null) {
      testimonialRepository.delete(testimonial)
    }
    return ResponseEntity(HttpStatus.NO_CONTENT)
  }
}
