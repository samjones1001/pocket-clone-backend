package com.example.pocketcloneapi.articles.persistance

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class ArticleRepositoryTests(@Autowired val articleRepository: ArticlesRepository) {
    @BeforeEach
    fun setup() {
        articleRepository.deleteAll()
    }

    @Test
    fun `saves and loads an article`() {
        val savedArticle = articleRepository.save(Article(url = "wwww.example.com"))
        val firstFound = articleRepository.findAll()[0]

        assertEquals(savedArticle.id, firstFound.id)
        assertEquals("wwww.example.com", firstFound.url)
    }
}