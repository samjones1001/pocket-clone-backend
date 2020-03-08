package com.example.pocketcloneapi.recommendations

import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecommendationsServiceTest(
    @Autowired val recommendationsService: RecommendationsService
) {

    @MockBean
    private lateinit var mockHttpConnection: HttpConnection

    @Test
    fun `transforms json response into list of Recommendations`() {
        val guardianSecretKey = System.getenv("GUARDIAN_SECRET_KEY") ?: ""
        val response = JSONObject("""{"response":{"results":[{"webTitle":"Test Title", "webUrl":"http://www.test.com"}]}}""")
        Mockito.`when`(mockHttpConnection.get(
            "https://content.guardianapis.com/search?api-key=$guardianSecretKey")
        ).thenReturn(response)

        val recommendations = recommendationsService.get()

        Assertions.assertEquals("Test Title", recommendations[0].title)
        Assertions.assertEquals("http://www.test.com", recommendations[0].url)
    }
}