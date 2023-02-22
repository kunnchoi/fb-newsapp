package com.jandas.newsapp.ui.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.jandas.newsapp.R
import com.jandas.newsapp.data.Result
import com.jandas.newsapp.data.models.News
import com.jandas.newsapp.data.models.NewsResponse
import com.jandas.newsapp.data.repository.NewsRepository
import com.jandas.newsapp.util.Async
import com.jandas.newsapp.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

/**
 * UiState for the news list screen.
 */
data class NewsUiState(
    val items: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val message: Int? = null
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    val imageLoader: ImageLoader
) : ViewModel() {

    private val _message: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)


    private val _newsTasksAsync = newsRepository.getNewsStream()
        .map { handleResult(it) }
        .mapLatest { Async.Success(it) }
        .onStart<Async<List<News>>> { emit(Async.Loading) }


    val uiState: StateFlow<NewsUiState> =
        combine(_isLoading, _message, _newsTasksAsync) { isLoading, message, newsAsync ->
            when (newsAsync) {
                Async.Loading -> {
                    NewsUiState(isLoading = true)
                }
                is Async.Success -> {
                    Timber.i("News loading success.")
                    NewsUiState(
                        items = newsAsync.data,
                        isLoading = isLoading,
                        message = message
                    )
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = NewsUiState(isLoading = true)
            )


    private fun handleResult(
        newsResult: Result<NewsResponse>
    ): List<News> = when(newsResult) {
        is Result.Success -> {
            removeMessage()
            newsResult.data.data
        }
        is Result.Error -> {
            Timber.e("Error loading news.")
            showMessage(R.string.news_loading_error)
            emptyList()
        }
    }

    private fun showMessage(messageRes: Int) {
        _message.value = messageRes
    }

    fun removeMessage() {
        _message.value = null
    }

}