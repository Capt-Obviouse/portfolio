package com.jduncan.portfolio.dto

import com.jduncan.portfolio.model.TechnologyCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class TechnologyResponse(
  val id: Long,
  val name: String,
  val category: TechnologyCategory,
  val description: String?,
  val iconUrl: String?,
  @field:Schema(
    description = "Proficiency level from 1 to 10",
    minimum = "1",
    maximum = "10",
    example = "5"
  )
  val proficiencyLevel: Int?,
  val yearsOfExperience: Double?,
  val isPublished: Boolean,
  val publishedDate: LocalDateTime,
  val lastModifiedDate: LocalDateTime
)
