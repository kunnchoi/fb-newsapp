package com.jandas.newsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jandas.newsapp.ui.screen.news.NewsScreen


@Composable
fun NewsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NewsDestinations.NEWS_ROUTE
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NewsDestinations.NEWS_ROUTE
        ) { _ ->
            NewsScreen()
        }

    }
}