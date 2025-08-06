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
import com.jduncan.portfolio.model.Technology
import com.jduncan.portfolio.repo.TechnologyRepository
import com.jduncan.portfolio.constants.TechnologyConstants

@RestController
@RequestMapping("/api/technologies")
class TechnologyController(
    private val technologyRepository: TechnologyRepository
) {

    @GetMapping
    fun getTechnologies(): List<Technology> {
        return technologyRepository.findAll()
    }

    @PostMapping
    fun createTechnology(@RequestBody technology: Technology): Technology {
        return technologyRepository.save(technology)
    }

    @PutMapping("/{id}")
    fun updateTechnology(@PathVariable id: Long, @RequestBody technology: Technology): Technology {
        val existingTechnology = technologyRepository.findById(id).orElse(null)
        if (existingTechnology != null) {
            return technologyRepository.save(technology.copy(id = id))
        }
        throw RuntimeException("Technology not found")
    }

    @DeleteMapping("/{id}")
    fun deleteTechnology(@PathVariable id: Long): ResponseEntity<Void> {
        val technology = technologyRepository.findById(id).orElse(null)
        if (technology != null) {
            technologyRepository.delete(technology)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
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
