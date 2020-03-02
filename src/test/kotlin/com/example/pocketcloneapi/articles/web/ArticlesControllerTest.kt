package com.example.pocketcloneapi.articles.web

import com.example.pocketcloneapi.articles.persistance.Article
import com.example.pocketcloneapi.articles.persistance.ArticlesRepository
import com.example.pocketcloneapi.articles.support.TestHelpers
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticlesControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val articlesRepository: ArticlesRepository,
    @Autowired val testHelpers: TestHelpers
) {

    @BeforeEach
    fun setup() {
        testHelpers.clearDataBase()
    }

    @Test
    fun `saves an article`() {
        val article = Article("www.example.com")

        mockMvc.perform(post("/api/articles")
            .content(objectMapper.writeValueAsString(article))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val foundArticle = articlesRepository.findAll()[0]
        assertEquals(foundArticle.url, "www.example.com")
    }

    @Test
    fun `loads all articles`() {
        articlesRepository.save(Article(url = "www.example.com"))
        articlesRepository.save(Article(url = "www.anotherWebsite.com"))

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/articles")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
    }
}