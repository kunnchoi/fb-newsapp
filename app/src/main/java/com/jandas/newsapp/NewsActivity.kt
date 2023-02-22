package com.jandas.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jandas.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * Main activity for the News App
 */
@AndroidEntryPoint
class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
              NewsNavGraph()
            }
        }
    }
}
