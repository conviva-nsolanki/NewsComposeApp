package com.example.newscomposeapp.data

import com.example.newscomposeapp.utils.BASE_URL_IMAGE

data class SearchResultResponse(
    val response: DocsResponse
) {

    fun toArticleList(): List<Article> {
        val articles = arrayListOf<Article>()
        for (result in response.docs) {
            val article = Article(
                url = result.webUrl,
                publishDate = result.publishData,
                title = result.headline.title,
                abstract = result.abstract,
                imageUrl = BASE_URL_IMAGE + (result.mediaList.firstOrNull()?.url?: "")
            )
            articles.add(article)
        }
        return articles
    }
}
