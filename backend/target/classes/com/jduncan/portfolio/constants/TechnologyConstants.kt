package com.jduncan.portfolio.constants

import com.jduncan.portfolio.model.TechnologyCategory

/**
 * Constants and utilities for technology-related data. This provides easy access to technology
 * categories and related information that can be used throughout the application, including API
 * responses for the frontend.
 */
object TechnologyConstants {

  /** All available technology categories */
  val ALL_CATEGORIES: List<TechnologyCategory> = TechnologyCategory.values().toList()

  /** All category display names as a list */
  val ALL_CATEGORY_NAMES: List<String> = TechnologyCategory.getAllDisplayNames()

  /** Category mapping from enum name to display name */
  val CATEGORY_MAP: Map<String, String> = TechnologyCategory.getCategoryMap()

  /** Category information as a list of maps for API responses */
  val CATEGORY_INFO: List<Map<String, Any>> =
    TechnologyCategory.values().map { category ->
      mapOf(
        "name" to category.name,
        "displayName" to category.displayName,
        "description" to category.description
      )
    }

  /** Get category by display name */
  fun getCategoryByDisplayName(displayName: String): TechnologyCategory? {
    return TechnologyCategory.fromDisplayName(displayName)
  }

  /** Check if a display name is a valid category */
  fun isValidCategory(displayName: String): Boolean {
    return getCategoryByDisplayName(displayName) != null
  }
}
