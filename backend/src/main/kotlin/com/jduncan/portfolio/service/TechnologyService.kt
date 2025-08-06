package com.jduncan.portfolio.service

import com.jduncan.portfolio.constants.TechnologyConstants
import com.jduncan.portfolio.dto.CreateTechnologyRequest
import com.jduncan.portfolio.dto.TechnologyResponse
import com.jduncan.portfolio.dto.UpdateTechnologyRequest
import com.jduncan.portfolio.mapper.TechnologyMapper.toEntity
import com.jduncan.portfolio.mapper.TechnologyMapper.toResponse
import com.jduncan.portfolio.mapper.TechnologyMapper.toResponseList
import com.jduncan.portfolio.mapper.TechnologyMapper.updateFromRequest
import com.jduncan.portfolio.model.TechnologyCategory
import com.jduncan.portfolio.repo.TechnologyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TechnologyService(private val technologyRepository: TechnologyRepository) {

  @Transactional(readOnly = true)
  fun getAllTechnologies(): List<TechnologyResponse> {
    return technologyRepository.findAll().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getTechnologyById(id: Long): TechnologyResponse? {
    return technologyRepository.findById(id).orElse(null)?.toResponse()
  }

  /** Get all technologies by category */
  @Transactional(readOnly = true)
  fun getTechnologiesByCategory(categoryName: String): List<TechnologyResponse> {
    val category = TechnologyCategory.fromDisplayName(categoryName)
    return if (category != null) {
      technologyRepository.findByCategory(category).toResponseList()
    } else {
      emptyList()
    }
  }

  /** Get all technologies grouped by category */
  @Transactional(readOnly = true)
  fun getTechnologiesGroupedByCategory(): Map<String, List<TechnologyResponse>> {
    return TechnologyCategory.values().associate { category ->
      category.displayName to technologyRepository.findByCategory(category).toResponseList()
    }
  }

  /** Get technologies by multiple categories */
  @Transactional(readOnly = true)
  fun getTechnologiesByCategories(categoryNames: List<String>): List<TechnologyResponse> {
    val categories = categoryNames.mapNotNull { name -> TechnologyCategory.fromDisplayName(name) }
    return technologyRepository.findByCategoryIn(categories).toResponseList()
  }

  /** Create a technology with category validation */
  fun createTechnology(request: CreateTechnologyRequest): TechnologyResponse {
    // Validate that the category is valid
    if (!TechnologyConstants.ALL_CATEGORIES.contains(request.category)) {
      throw IllegalArgumentException("Invalid technology category: ${request.category}")
    }
    val entity = request.toEntity()
    val savedEntity = technologyRepository.save(entity)
    return savedEntity.toResponse()
  }

  fun updateTechnology(id: Long, request: UpdateTechnologyRequest): TechnologyResponse? {
    val existingTechnology = technologyRepository.findById(id).orElse(null)
    return existingTechnology?.let {
      // Validate category if provided
      request.category?.let { category ->
        if (!TechnologyConstants.ALL_CATEGORIES.contains(category)) {
          throw IllegalArgumentException("Invalid technology category: $category")
        }
      }
      val updatedEntity = it.updateFromRequest(request)
      val savedEntity = technologyRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun deleteTechnology(id: Long): Boolean {
    return if (technologyRepository.existsById(id)) {
      technologyRepository.deleteById(id)
      true
    } else {
      false
    }
  }

  /** Validate if a category name is valid */
  fun isValidCategory(categoryName: String): Boolean {
    return TechnologyConstants.isValidCategory(categoryName)
  }

  /** Get all available categories */
  @Transactional(readOnly = true)
  fun getAllCategories(): List<TechnologyCategory> {
    return TechnologyConstants.ALL_CATEGORIES
  }

  fun publishTechnology(id: Long): TechnologyResponse? {
    val existingTechnology = technologyRepository.findById(id).orElse(null)
    return existingTechnology?.let {
      val updatedEntity =
        it.copy(isPublished = true, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = technologyRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun unpublishTechnology(id: Long): TechnologyResponse? {
    val existingTechnology = technologyRepository.findById(id).orElse(null)
    return existingTechnology?.let {
      val updatedEntity =
        it.copy(isPublished = false, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = technologyRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }
}
