package com.jandas.newsapp.di

import com.jandas.newsapp.data.repository.NewsRepository
import com.jandas.newsapp.data.source.remote.NewsDataSource
import com.jandas.newsapp.data.source.repositoryimpl.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        @NewsService newsSource: NewsDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NewsRepository {
        return NewsRepositoryImpl(newsSource, ioDispatcher)
    }
}

