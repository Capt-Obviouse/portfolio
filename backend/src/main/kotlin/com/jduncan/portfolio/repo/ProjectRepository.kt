package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun findByName(name: String): Project?
}

