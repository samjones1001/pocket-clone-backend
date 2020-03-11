package com.example.pocketcloneapi.recommendations

import com.example.pocketcloneapi.recommendations.web.Recommendation
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired

@Service
class RecommendationsService(@Autowired private val http: HttpConnection) {
    var guardianSecretKey = System.getenv("GUARDIAN_SECRET_KEY") ?: ""

    fun get(): List<Recommendation> {
        val response = http.get("https://content.guardianapis.com/search?api-key=$guardianSecretKey")
            .getJSONObject("response")
            .getJSONArray("results")

        return recommendationsFromJsonResponse(response)
    }

    private fun recommendationsFromJsonResponse(jsonResponse: JSONArray): List<Recommendation> {
        val recommendations = mutableListOf<JSONObject>()

        for(i in 0 until jsonResponse.length()) {
            val result = jsonResponse.getJSONObject(i)
            recommendations.add(result)
        }

        return recommendations.map { Recommendation(
            title = it.getString("webTitle"),
            url = it.getString("webUrl")
        )}
    }
}

@Component
class HttpConnection() {
    fun get(url: String): JSONObject = khttp.get(url).jsonObject
}