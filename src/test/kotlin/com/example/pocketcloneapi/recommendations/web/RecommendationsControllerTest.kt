package com.example.pocketcloneapi.recommendations.web

import com.example.pocketcloneapi.recommendations.RecommendationsService
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecommendationsControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var mockRecommendationsService: RecommendationsService

    @Test
    fun `retrieves recommendations from an external api`() {
        val response: List<Recommendation> = listOf(Recommendation(title = "Test Title", url = "http://www.test-url.com"))
        Mockito.`when`(mockRecommendationsService.get()).thenReturn(response)

        val apiResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        assertEquals("""[{"title":"Test Title","url":"http://www.test-url.com"}]""", apiResponse.response.contentAsString)
    }
}