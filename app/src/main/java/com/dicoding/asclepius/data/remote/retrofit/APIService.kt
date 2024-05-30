package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("top-headlines?q=cancer&category=health&language=en&apiKey=0730bc50fc454b1c9f246f46a19e3c5b")
    fun getArticles(): Call<ArticlesResponse>
}