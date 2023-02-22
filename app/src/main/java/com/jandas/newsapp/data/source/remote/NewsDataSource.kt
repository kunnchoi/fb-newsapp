package com.jandas.newsapp.data.source.remote

import com.jandas.newsapp.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface service for News Data Source
 */
interface NewsDataSource {
   @GET("v1/gifs/trending")
   suspend fun getNews(@Query("api_key") apiKey: String) : Response<NewsResponse>
}