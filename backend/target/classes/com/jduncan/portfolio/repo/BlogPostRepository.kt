package com.jduncan.portfolio.repo

import com.jduncan.portfolio.model.BlogPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogPostRepository : JpaRepository<BlogPost, Long> {
  fun findByIsPublishedTrueOrderByPublishedDateDesc(): List<BlogPost>

  fun findByIsPublishedFalseOrderByPublishedDateDesc(): List<BlogPost>
}
