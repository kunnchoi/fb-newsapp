package com.jandas.newsapp

import com.jandas.newsapp.data.repository.NewsRepository
import com.jandas.newsapp.data.Result
import com.jandas.newsapp.data.models.ImageMeta
import com.jandas.newsapp.data.models.News
import com.jandas.newsapp.data.models.NewsResponse
import kotlinx.coroutines.flow.*

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeRepository : NewsRepository {

    private var shouldReturnError = false
    private var _savedNews = NewsResponse()

    fun mockNewsResponse() {
        _savedNews = NewsResponse(data = mutableListOf<News>().apply {
            add(
                News(
                    id = "sadfa",
                    title = "First News",
                    images = com.jandas.newsapp.data.models.Images(
                        downsized = ImageMeta(
                            height = "400",
                            width = "496",
                            url = "https://media3.giphy.com/media/sPdXjjL00ScHldihJn/giphy.gif"
                        )
                    )
                )
            )
        })
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun getNewsStream(): Flow<Result<NewsResponse>> {
        return flow {
            emit(_savedNews.let {
                if (shouldReturnError) {
                    Result.Error(Exception())
                } else {
                    Result.Success(_savedNews)
                }
            })
        }
    }

}
