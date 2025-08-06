package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.Testimonial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestimonialRepository : JpaRepository<Testimonial, Long> {
  fun findByIsPublishedTrueOrderByTestimonialDateDesc(): List<Testimonial>

  fun findByIsPublishedFalseOrderByTestimonialDateDesc(): List<Testimonial>
}
