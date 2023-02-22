package com.jandas.newsapp.data.source.repositoryimpl

import com.jandas.newsapp.data.Result
import com.jandas.newsapp.data.models.NewsResponse
import com.jandas.newsapp.data.repository.NewsRepository
import com.jandas.newsapp.data.source.remote.API_KEY
import com.jandas.newsapp.data.source.remote.NewsDataSource
import com.jandas.newsapp.data.source.remote.request
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * News repository implementation class
 */
class NewsRepositoryImpl(
    private val newsDataSource: NewsDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {
    override fun getNewsStream(): Flow<Result<NewsResponse>> {
        return flow { emit(request { newsDataSource.getNews(API_KEY) }) }.flowOn(ioDispatcher)
    }
}