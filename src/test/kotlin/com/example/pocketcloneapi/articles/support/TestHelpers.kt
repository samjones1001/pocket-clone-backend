package com.example.pocketcloneapi.articles.support

import com.example.pocketcloneapi.articles.persistance.ArticlesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TestHelpers(
    @Autowired val articlesRepository: ArticlesRepository
) {

    fun clearDataBase() {
        articlesRepository.deleteAll()
    }
}