package com.jduncan.portfolio.dto

import com.jduncan.portfolio.model.TechnologyCategory
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class UpdateTechnologyRequest(
  val name: String? = null,
  val category: TechnologyCategory? = null,
  val description: String? = null,
  val iconUrl: String? = null,
  @field:Min(1)
  @field:Max(10)
  @field:Schema(
    description = "Proficiency level from 1 to 10",
    minimum = "1",
    maximum = "10",
    example = "5"
  )
  val proficiencyLevel: Int? = null,
  @field:Min(0)
  @field:Schema(description = "Years of experience", example = "2.5")
  val yearsOfExperience: Double? = null,
  val isPublished: Boolean? = null
)
