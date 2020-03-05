package com.example.pocketcloneapi.articles

import com.example.pocketcloneapi.articles.persistance.Article
import com.example.pocketcloneapi.articles.persistance.ArticlesRepository
import com.example.pocketcloneapi.articles.web.ArticleUpload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Service
class ArticleService(
    @Autowired private val articlesRepository: ArticlesRepository,
    @Autowired private val jsoup: JsoupConnection
) {

    fun findAll(): List<Article> = articlesRepository.findAll()

    fun create(articleUpload: ArticleUpload): Article {
        val article = Article(articleUpload.url, getArticleTitle(articleUpload.url))
        return articlesRepository.save(article)
    }

    fun delete(id: Long): Unit = articlesRepository.deleteById(id)

    private fun getArticleTitle(url: String): String {
        return jsoup.getTitle(url)
    }

}

@Component
class JsoupConnection() {
    fun getTitle(url: String): String = Jsoup.connect(url).get().title()
}