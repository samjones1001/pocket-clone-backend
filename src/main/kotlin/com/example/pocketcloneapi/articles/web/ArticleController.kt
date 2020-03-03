package com.example.pocketcloneapi.articles.web

import com.example.pocketcloneapi.articles.ArticleService
import com.example.pocketcloneapi.articles.persistance.Article
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/api")
class ArticleController(@Autowired private val articleService: ArticleService) {

    @GetMapping("/articles")
    fun findAll() : List<Article> = articleService.findAll()

    @PostMapping("/articles")
    fun create(@Valid @RequestBody article: Article) : Article = articleService.create(article)

    @DeleteMapping("/articles/{id}")
    fun delete(@PathVariable id: Long): Unit = articleService.delete(id)

}