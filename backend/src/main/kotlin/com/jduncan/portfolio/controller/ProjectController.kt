package com.jduncan.portfolio.controller

import com.jduncan.portfolio.dto.CreateProjectRequest
import com.jduncan.portfolio.dto.ProjectResponse
import com.jduncan.portfolio.dto.UpdateProjectRequest
import com.jduncan.portfolio.service.ProjectService
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/projects")
class ProjectController(private val projectService: ProjectService) {

  @GetMapping
  fun getPublishedProjects(): List<ProjectResponse> {
    return projectService.getAllPublishedProjects()
  }

  @GetMapping("/unpublished")
  fun getUnpublishedProjects(): List<ProjectResponse> {
    return projectService.getAllUnpublishedProjects()
  }

  @GetMapping("/{id}")
  fun getProject(@PathVariable id: Long): ResponseEntity<ProjectResponse> {
    val project = projectService.getProjectById(id)
    return if (project != null) {
      ResponseEntity.ok(project)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PostMapping
  fun createProject(
    @Valid @RequestBody request: CreateProjectRequest
  ): ResponseEntity<ProjectResponse> {
    val createdProject = projectService.createProject(request)
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProject)
  }

  @PutMapping("/{id}")
  fun updateProject(
    @PathVariable id: Long,
    @Valid @RequestBody request: UpdateProjectRequest
  ): ResponseEntity<ProjectResponse> {
    val updatedProject = projectService.updateProject(id, request)
    return if (updatedProject != null) {
      ResponseEntity.ok(updatedProject)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping("/{id}")
  fun deleteProject(@PathVariable id: Long): ResponseEntity<Void> {
    val deleted = projectService.deleteProject(id)
    return if (deleted) {
      ResponseEntity.noContent().build()
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/publish")
  fun publishProject(@PathVariable id: Long): ResponseEntity<ProjectResponse> {
    val publishedProject = projectService.publishProject(id)
    return if (publishedProject != null) {
      ResponseEntity.ok(publishedProject)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PutMapping("/{id}/unpublish")
  fun unpublishProject(@PathVariable id: Long): ResponseEntity<ProjectResponse> {
    val unpublishedProject = projectService.unpublishProject(id)
    return if (unpublishedProject != null) {
      ResponseEntity.ok(unpublishedProject)
    } else {
      ResponseEntity.notFound().build()
    }
  }
}
