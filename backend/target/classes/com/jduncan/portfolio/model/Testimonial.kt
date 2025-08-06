package com.jduncan.portfolio.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "testimonials")
data class Testimonial(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
  val name: String,
  val title: String,
  val company: String,
  val content: String,
  val imageUrl: String? = null,
  val isPublished: Boolean = false,
  val testimonialDate: LocalDateTime = LocalDateTime.now(),
  val lastModifiedDate: LocalDateTime = LocalDateTime.now()
)
