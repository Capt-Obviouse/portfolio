package com.jduncan.portfolio.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import com.jduncan.portfolio.model.Project
import com.jduncan.portfolio.repo.ProjectRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus  

@RestController
@RequestMapping("/api/projects")
class ProjectController {

    @Autowired
    private lateinit var projectRepository: ProjectRepository

    @GetMapping
    fun getProjects(): List<Project> {
        return projectRepository.findAll()
    }
    @PostMapping
    fun createProject(@RequestBody project: Project): Project {
        return projectRepository.save(project)
    }
    @PutMapping("/{id}")
    fun updateProject(@PathVariable id: Long, @RequestBody project: Project): Project {
        val existingProject = projectRepository.findById(id).orElse(null)
        if (existingProject != null) {
            return projectRepository.save(project.copy(id = id))
        }
        throw RuntimeException("Project not found")
    }
    @DeleteMapping("/{id}")
    fun deleteProject(@PathVariable id: Long): ResponseEntity<Void> {
        val project = projectRepository.findById(id).orElse(null)
        if (project != null) {
            projectRepository.delete(project)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}