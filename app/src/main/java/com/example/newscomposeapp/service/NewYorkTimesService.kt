package com.example.newscomposeapp.service

import com.example.newscomposeapp.data.SearchResultResponse
import com.example.newscomposeapp.utils.API_KEY_NEW_YORK_TIMES
import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query

interface NewYorkTimesService {

    // https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=yourkey
    @GET("search/v2/articlesearch.json")
    suspend fun searchArticle(@Query("q") query: String, @Query("api-key") apiKey: String = API_KEY_NEW_YORK_TIMES): SearchResultResponse

}