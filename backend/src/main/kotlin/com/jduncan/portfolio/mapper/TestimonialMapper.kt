package com.jduncan.portfolio.mapper

import com.jduncan.portfolio.dto.CreateTestimonialRequest
import com.jduncan.portfolio.dto.TestimonialResponse
import com.jduncan.portfolio.dto.UpdateTestimonialRequest
import com.jduncan.portfolio.model.Testimonial
import java.time.LocalDateTime

object TestimonialMapper {

  fun Testimonial.toResponse(): TestimonialResponse {
    return TestimonialResponse(
      id = this.id,
      name = this.name,
      title = this.title,
      company = this.company,
      content = this.content,
      imageUrl = this.imageUrl,
      isPublished = this.isPublished,
      testimonialDate = this.testimonialDate,
      lastModifiedDate = this.lastModifiedDate
    )
  }

  fun CreateTestimonialRequest.toEntity(): Testimonial {
    return Testimonial(
      name = this.name,
      title = this.title,
      company = this.company,
      content = this.content,
      imageUrl = this.imageUrl,
      isPublished = this.isPublished,
      testimonialDate = LocalDateTime.now(),
      lastModifiedDate = LocalDateTime.now()
    )
  }

  fun Testimonial.updateFromRequest(request: UpdateTestimonialRequest): Testimonial {
    return this.copy(
      name = request.name ?: this.name,
      title = request.title ?: this.title,
      company = request.company ?: this.company,
      content = request.content ?: this.content,
      imageUrl = request.imageUrl ?: this.imageUrl,
      isPublished = request.isPublished ?: this.isPublished,
      lastModifiedDate = LocalDateTime.now()
    )
  }

  fun List<Testimonial>.toResponseList(): List<TestimonialResponse> {
    return this.map { it.toResponse() }
  }
}
