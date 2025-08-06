package com.jduncan.portfolio.service

import com.jduncan.portfolio.constants.TechnologyConstants
import com.jduncan.portfolio.model.Technology
import com.jduncan.portfolio.model.TechnologyCategory
import com.jduncan.portfolio.repo.TechnologyRepository
import org.springframework.stereotype.Service

@Service
class TechnologyService(private val technologyRepository: TechnologyRepository) {

  /** Get all technologies by category */
  fun getTechnologiesByCategory(categoryName: String): List<Technology> {
    val category = TechnologyCategory.fromDisplayName(categoryName)
    return if (category != null) {
      technologyRepository.findByCategory(category)
    } else {
      emptyList()
    }
  }

  /** Get all technologies grouped by category */
  fun getTechnologiesGroupedByCategory(): Map<String, List<Technology>> {
    return TechnologyCategory.values().associate { category ->
      category.displayName to technologyRepository.findByCategory(category)
    }
  }

  /** Validate if a category name is valid */
  fun isValidCategory(categoryName: String): Boolean {
    return TechnologyConstants.isValidCategory(categoryName)
  }

  /** Get all available categories */
  fun getAllCategories(): List<TechnologyCategory> {
    return TechnologyConstants.ALL_CATEGORIES
  }

  /** Get technologies by multiple categories */
  fun getTechnologiesByCategories(categoryNames: List<String>): List<Technology> {
    val categories = categoryNames.mapNotNull { name -> TechnologyCategory.fromDisplayName(name) }
    return technologyRepository.findByCategoryIn(categories)
  }

  /** Create a technology with category validation */
  fun createTechnology(technology: Technology): Technology {
    // Validate that the category is valid
    if (!TechnologyConstants.ALL_CATEGORIES.contains(technology.category)) {
      throw IllegalArgumentException("Invalid technology category: ${technology.category}")
    }
    return technologyRepository.save(technology)
  }
}
