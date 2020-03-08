package com.example.pocketcloneapi.recommendations.web

import com.example.pocketcloneapi.recommendations.RecommendationsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api")
class RecommendationsController(@Autowired private val recommendationsService: RecommendationsService) {

    @GetMapping("/recommendations")
    fun getRecommendations():List<Recommendation> = recommendationsService.get()
}