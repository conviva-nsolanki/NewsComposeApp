package com.example.newscomposeapp.data

import com.google.gson.annotations.SerializedName

data class HeadlineResponse(
    @SerializedName("main")
    val title: String
)
