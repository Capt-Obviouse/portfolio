package com.jduncan.portfolio.service

import com.jduncan.portfolio.dto.CreateProjectRequest
import com.jduncan.portfolio.dto.ProjectResponse
import com.jduncan.portfolio.dto.UpdateProjectRequest
import com.jduncan.portfolio.mapper.ProjectMapper.toEntity
import com.jduncan.portfolio.mapper.ProjectMapper.toResponse
import com.jduncan.portfolio.mapper.ProjectMapper.toResponseList
import com.jduncan.portfolio.mapper.ProjectMapper.updateFromRequest
import com.jduncan.portfolio.repo.ProjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProjectService(private val projectRepository: ProjectRepository) {

  @Transactional(readOnly = true)
  fun getAllPublishedProjects(): List<ProjectResponse> {
    return projectRepository.findByIsPublishedTrueOrderByPublishedDateDesc().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getAllUnpublishedProjects(): List<ProjectResponse> {
    return projectRepository.findByIsPublishedFalseOrderByPublishedDateDesc().toResponseList()
  }

  @Transactional(readOnly = true)
  fun getProjectById(id: Long): ProjectResponse? {
    return projectRepository.findById(id).orElse(null)?.toResponse()
  }

  fun createProject(request: CreateProjectRequest): ProjectResponse {
    val entity = request.toEntity()
    val savedEntity = projectRepository.save(entity)
    return savedEntity.toResponse()
  }

  fun updateProject(id: Long, request: UpdateProjectRequest): ProjectResponse? {
    val existingProject = projectRepository.findById(id).orElse(null)
    return existingProject?.let {
      val updatedEntity = it.updateFromRequest(request)
      val savedEntity = projectRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun deleteProject(id: Long): Boolean {
    return if (projectRepository.existsById(id)) {
      projectRepository.deleteById(id)
      true
    } else {
      false
    }
  }

  fun publishProject(id: Long): ProjectResponse? {
    val existingProject = projectRepository.findById(id).orElse(null)
    return existingProject?.let {
      val updatedEntity =
        it.copy(isPublished = true, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = projectRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }

  fun unpublishProject(id: Long): ProjectResponse? {
    val existingProject = projectRepository.findById(id).orElse(null)
    return existingProject?.let {
      val updatedEntity =
        it.copy(isPublished = false, lastModifiedDate = java.time.LocalDateTime.now())
      val savedEntity = projectRepository.save(updatedEntity)
      savedEntity.toResponse()
    }
  }
}
