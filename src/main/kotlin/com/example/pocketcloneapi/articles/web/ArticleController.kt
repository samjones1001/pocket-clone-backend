package com.example.pocketcloneapi.articles.web

import com.example.pocketcloneapi.articles.ArticleService
import com.example.pocketcloneapi.articles.persistance.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(@Autowired private val articleService: ArticleService) {

    @PostMapping("/articles")
    fun create(@Valid @RequestBody article: Article) : Article = articleService.create(article)
}