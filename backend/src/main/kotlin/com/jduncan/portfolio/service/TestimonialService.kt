package com.jduncan.portfolio.service

import com.jduncan.portfolio.dto.CreateTestimonialRequest
import com.jduncan.portfolio.dto.TestimonialResponse
import com.jduncan.portfolio.dto.UpdateTestimonialRequest
import com.jduncan.portfolio.mapper.TestimonialMapper.toEntity
import com.jduncan.portfolio.mapper.TestimonialMapper.toResponse
import com.jduncan.portfolio.mapper.TestimonialMapper.toResponseList
import com.jduncan.portfolio.mapper.TestimonialMapper.updateFromRequest
import com.jduncan.portfolio.repo.TestimonialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TestimonialService(private val testimonialRepository: TestimonialRepository) {

  @Transactional(readOnly = true)
  fun getAllPublishedTestimonials(): List<TestimonialResponse> {
    return testimonialRepository.findByIsPublishedTrueOrderByTestimonialDateDesc().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getAllUnpublishedTestimonials(): List<TestimonialResponse> {
    return testimonialRepository.findByIsPublishedFalseOrderByTestimonialDateDesc().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getTestimonialById(id: Long): TestimonialResponse? {
    return testimonialRepository.findById(id).orElse(null)?.toResponse()
  }

  fun createTestimonial(request: CreateTestimonialRequest): TestimonialResponse {
    val entity = request.toEntity()
    val savedEntity = testimonialRepository.save(entity)
    return savedEntity.toResponse()
  }

  fun updateTestimonial(id: Long, request: UpdateTestimonialRequest): TestimonialResponse? {
    val existingTestimonial = testimonialRepository.findById(id).orElse(null)
    return existingTestimonial?.let {
      val updatedEntity = it.updateFromRequest(request)
      val savedEntity = testimonialRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun deleteTestimonial(id: Long): Boolean {
    return if (testimonialRepository.existsById(id)) {
      testimonialRepository.deleteById(id)
      true
    } else {
      false
    }
  }

  fun publishTestimonial(id: Long): TestimonialResponse? {
    val existingTestimonial = testimonialRepository.findById(id).orElse(null)
    return existingTestimonial?.let {
      val updatedEntity =
        it.copy(isPublished = true, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = testimonialRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun unpublishTestimonial(id: Long): TestimonialResponse? {
    val existingTestimonial = testimonialRepository.findById(id).orElse(null)
    return existingTestimonial?.let {
      val updatedEntity =
        it.copy(isPublished = false, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = testimonialRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }
}
