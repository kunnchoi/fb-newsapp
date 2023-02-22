package com.jandas.newsapp.data.repository

import com.jandas.newsapp.data.Result
import com.jandas.newsapp.data.models.NewsResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface for news data layer
 */
interface NewsRepository {
    fun getNewsStream(): Flow<Result<NewsResponse>>
}