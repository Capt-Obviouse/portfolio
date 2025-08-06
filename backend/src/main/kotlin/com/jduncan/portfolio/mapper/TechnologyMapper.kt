package com.jduncan.portfolio.mapper

import com.jduncan.portfolio.dto.CreateTechnologyRequest
import com.jduncan.portfolio.dto.TechnologyResponse
import com.jduncan.portfolio.dto.UpdateTechnologyRequest
import com.jduncan.portfolio.model.Technology
import java.time.LocalDateTime

object TechnologyMapper {

  fun Technology.toResponse(): TechnologyResponse {
    return TechnologyResponse(
      id = this.id,
      name = this.name,
      category = this.category,
      description = this.description,
      iconUrl = this.iconUrl,
      proficiencyLevel = this.proficiencyLevel,
      yearsOfExperience = this.yearsOfExperience,
      isPublished = this.isPublished,
      publishedDate = this.publishedDate,
      lastModifiedDate = this.lastModifiedDate
    )
  }

  fun CreateTechnologyRequest.toEntity(): Technology {
    return Technology(
      name = this.name,
      category = this.category,
      description = this.description,
      iconUrl = this.iconUrl,
      proficiencyLevel = this.proficiencyLevel,
      yearsOfExperience = this.yearsOfExperience,
      isPublished = this.isPublished,
      publishedDate = LocalDateTime.now(),
      lastModifiedDate = LocalDateTime.now()
    )
  }

  fun Technology.updateFromRequest(request: UpdateTechnologyRequest): Technology {
    return this.copy(
      name = request.name ?: this.name,
      category = request.category ?: this.category,
      description = request.description ?: this.description,
      iconUrl = request.iconUrl ?: this.iconUrl,
      proficiencyLevel = request.proficiencyLevel ?: this.proficiencyLevel,
      yearsOfExperience = request.yearsOfExperience ?: this.yearsOfExperience,
      isPublished = request.isPublished ?: this.isPublished,
      lastModifiedDate = LocalDateTime.now()
    )
  }

  fun List<Technology>.toResponseList(): List<TechnologyResponse> {
    return this.map { it.toResponse() }
  }
}
