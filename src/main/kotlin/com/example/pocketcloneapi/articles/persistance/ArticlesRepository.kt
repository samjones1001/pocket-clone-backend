package com.example.pocketcloneapi.articles.persistance

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*

@Repository
interface ArticlesRepository : JpaRepository<Article, Long>{}

@Entity
@Table(name="articles")
data class Article (
    val url: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0
)