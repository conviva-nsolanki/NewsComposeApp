package com.example.newscomposeapp.data.repository

import com.example.newscomposeapp.data.Article
import com.example.newscomposeapp.service.NewYorkTimesService
import javax.inject.Inject

class NewYorkTimesRepository @Inject constructor(private val service: NewYorkTimesService) {

    suspend fun getSearchResult(query: String): List<Article> {
        val response = service.searchArticle(query)
        return response.toArticleList()
    }
}