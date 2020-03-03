package com.example.pocketcloneapi.articles

import com.example.pocketcloneapi.articles.persistance.Article
import com.example.pocketcloneapi.articles.persistance.ArticlesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleService(@Autowired private val articlesRepository: ArticlesRepository) {

    fun findAll(): List<Article> = articlesRepository.findAll()

    fun create(article: Article): Article = articlesRepository.save(article)

    fun delete(id: Long): Unit = articlesRepository.deleteById(id)

}