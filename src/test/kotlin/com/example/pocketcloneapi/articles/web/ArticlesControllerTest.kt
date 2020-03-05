package com.example.pocketcloneapi.articles.web

import com.example.pocketcloneapi.articles.ArticleService
import com.example.pocketcloneapi.articles.JsoupConnection
import com.example.pocketcloneapi.articles.persistance.Article
import com.example.pocketcloneapi.articles.persistance.ArticlesRepository
import com.example.pocketcloneapi.articles.support.TestHelpers
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
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

    @MockBean
    private lateinit var mockJsoupConnection: JsoupConnection

    @BeforeEach
    fun setup() {
        testHelpers.clearDataBase()
    }

    @Test
    fun `saves an article with a url and a title`() {
        val article = ArticleUpload("http://www.example.com")
        Mockito.`when`(mockJsoupConnection.getTitle(article.url)).thenReturn("Page Title")

        mockMvc.perform(post("/api/articles")
            .content(objectMapper.writeValueAsString(article))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val foundArticle = articlesRepository.findAll()[0]
        assertEquals("http://www.example.com", foundArticle.url)
        assertEquals("Page Title", foundArticle.title)
    }

    @Test
    fun `loads all articles`() {
        articlesRepository.save(Article(url = "www.example.com", title = "Test site"))
        articlesRepository.save(Article(url = "www.anotherWebsite.com", title = "Another test site"))

        mockMvc.perform(get("http://localhost:8080/api/articles")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
    }

    @Test
    fun `deletes an article`() {
        val id = articlesRepository.save(Article(url = "www.example.com", title = "Test site")).id

        mockMvc.perform(delete("/api/articles/${id}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val articles = articlesRepository.findAll()
        assertEquals(0, articles.size)
    }
}