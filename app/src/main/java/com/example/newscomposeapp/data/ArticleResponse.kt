package com.example.newscomposeapp.data

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val abstract: String,
    @SerializedName("web_url")
    val webUrl: String,
    @SerializedName("pub_date")
    val publishData: String,
    @SerializedName("multimedia")
    val mediaList: List<MediaMetadataResponse>,
    val headline: HeadlineResponse
)
