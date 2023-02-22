package com.jandas.newsapp.ui.screen.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jandas.newsapp.R
import com.jandas.newsapp.data.models.News

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewsContent(
        loading = uiState.isLoading,
        newsList = uiState.items,
        imageLoader = viewModel.imageLoader
    )

    uiState.message?.let { message ->
        Snackbar(modifier = Modifier.padding(4.dp)) {
            Text(text = stringResource(message))
        }
    }
}

@Composable
fun NewsContent(loading: Boolean, newsList: List<News>, imageLoader: ImageLoader) {
    if (loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            itemsIndexed(items = newsList) { _, item ->
                NewsItem(news = item, imageLoader = imageLoader)
            }
        }
    }
}

@Composable
fun NewsItem(news: News, imageLoader: ImageLoader) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.vertical_margin),
            ), elevation = 4.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(all = 8.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = news.images.downsized.url).apply(
                            block = { size(Size.ORIGINAL) }
                        ).build(), imageLoader = imageLoader
                ),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(news.images.downsized.getAspectRatio()),
            )
        }
    }

}