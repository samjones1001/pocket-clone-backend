package com.example.pocketcloneapi.articles.persistance

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.*
import javax.transaction.Transactional

@Repository
interface ArticlesRepository : JpaRepository<Article, Long>{
    @Transactional
    @Modifying
    @Query("UPDATE Article a SET a.isRead=?2 WHERE a.id=?1")
    fun update(id: Long, isRead: Boolean)
}

@Entity
@Table(name="articles")
data class Article (
    val url: String,

    val title: String,

    val isRead: Boolean = false,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = 0
)