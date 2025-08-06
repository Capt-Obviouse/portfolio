package com.jduncan.portfolio.controller

import com.jduncan.portfolio.constants.TechnologyConstants
import com.jduncan.portfolio.dto.CreateTechnologyRequest
import com.jduncan.portfolio.dto.TechnologyResponse
import com.jduncan.portfolio.dto.UpdateTechnologyRequest
import com.jduncan.portfolio.service.TechnologyService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/technologies")
class TechnologyController(private val technologyService: TechnologyService) {

  @GetMapping
  fun getTechnologies(): List<TechnologyResponse> {
    return technologyService.getAllTechnologies()
  }

  @GetMapping("/{id}")
  fun getTechnology(@PathVariable id: Long): ResponseEntity<TechnologyResponse> {
    val technology = technologyService.getTechnologyById(id)
    return if (technology != null) {
      ResponseEntity.ok(technology)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @GetMapping("/by-category")
  fun getTechnologiesByCategory(@RequestParam categoryName: String): List<TechnologyResponse> {
    return technologyService.getTechnologiesByCategory(categoryName)
  }

  @GetMapping("/grouped")
  fun getTechnologiesGroupedByCategory(): Map<String, List<TechnologyResponse>> {
    return technologyService.getTechnologiesGroupedByCategory()
  }

  @PostMapping
  fun createTechnology(
    @Valid @RequestBody request: CreateTechnologyRequest
  ): ResponseEntity<TechnologyResponse> {
    return try {
      val createdTechnology = technologyService.createTechnology(request)
      ResponseEntity.status(HttpStatus.CREATED).body(createdTechnology)
    } catch (e: IllegalArgumentException) {
      ResponseEntity.badRequest().build()
    }
  }

  @PutMapping("/{id}")
  fun updateTechnology(
    @PathVariable id: Long,
    @Valid @RequestBody request: UpdateTechnologyRequest
  ): ResponseEntity<TechnologyResponse> {
    return try {
      val updatedTechnology = technologyService.updateTechnology(id, request)
      if (updatedTechnology != null) {
        ResponseEntity.ok(updatedTechnology)
      } else {
        ResponseEntity.notFound().build()
      }
    } catch (e: IllegalArgumentException) {
      ResponseEntity.badRequest().build()
    }
  }

  @DeleteMapping("/{id}")
  fun deleteTechnology(@PathVariable id: Long): ResponseEntity<Void> {
    val deleted = technologyService.deleteTechnology(id)
    return if (deleted) {
      ResponseEntity.noContent().build()
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/publish")
  fun publishTechnology(@PathVariable id: Long): ResponseEntity<TechnologyResponse> {
    val publishedTechnology = technologyService.publishTechnology(id)
    return if (publishedTechnology != null) {
      ResponseEntity.ok(publishedTechnology)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/unpublish")
  fun unpublishTechnology(@PathVariable id: Long): ResponseEntity<TechnologyResponse> {
    val unpublishedTechnology = technologyService.unpublishTechnology(id)
    return if (unpublishedTechnology != null) {
      ResponseEntity.ok(unpublishedTechnology)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @GetMapping("/categories")
  fun getTechnologyCategories(): Map<String, Any> {
    return mapOf(
      "categories" to TechnologyConstants.CATEGORY_INFO,
      "categoryNames" to TechnologyConstants.ALL_CATEGORY_NAMES,
      "categoryMap" to TechnologyConstants.CATEGORY_MAP
    )
  }
}
