package com.example.pocketcloneapi.articles.persistance

import com.example.pocketcloneapi.articles.support.TestHelpers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
class ArticleRepositoryTests(
        @Autowired val articleRepository: ArticlesRepository,
        @Autowired val testHelpers: TestHelpers
) {

    @BeforeEach
    fun setup() {
        testHelpers.clearDataBase()
    }

    @Test
    fun `saves and loads an article`() {
        val savedArticle = articleRepository.save(Article(url = "wwww.example.com"))
        val firstFound = articleRepository.findAll()[0]

        assertEquals(savedArticle.id, firstFound.id)
        assertEquals("wwww.example.com", firstFound.url)
    }

    @Test
    fun `deletes an article`() {
        val id = articleRepository.save(Article(url = "wwww.example.com")).id
        articleRepository.deleteById(id)
        val articles = articleRepository.findAll()

        assertEquals(0, articles.size)
    }
}