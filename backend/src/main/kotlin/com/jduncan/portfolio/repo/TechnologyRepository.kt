package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.Technology
import com.jduncan.portfolio.model.TechnologyCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TechnologyRepository : JpaRepository<Technology, Long> {
  fun findByIsPublishedTrueOrderByPublishedDateDesc(): List<Technology>

  fun findByIsPublishedFalseOrderByPublishedDateDesc(): List<Technology>

  fun findByProficiencyLevel(proficiencyLevel: Int): List<Technology>

  fun findByCategory(category: TechnologyCategory): List<Technology>

  fun findByCategoryIn(categories: List<TechnologyCategory>): List<Technology>

  fun findByYearsOfExperience(yearsOfExperience: Double): List<Technology>

  fun findByName(name: String): List<Technology>
}
