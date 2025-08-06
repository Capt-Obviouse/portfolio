package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
  fun findByIsPublishedTrueOrderByPublishedDateDesc(): List<Project>

  fun findByIsPublishedFalseOrderByPublishedDateDesc(): List<Project>
}
