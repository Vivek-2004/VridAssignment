package com.vrid.assignment.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.vrid.assignment.viewmodel.BlogViewModel

@Composable
fun ListScreenSelector(
    navController: NavController,
    viewModel: BlogViewModel
) {
    when {
        viewModel.isLoading -> LoadingScreen()
        viewModel.errorMessage != null ->
            ErrorScreen(
                errorMessage = viewModel.errorMessage!!
            )

        else ->
            BlogListScreen(
                navController = navController,
                posts = viewModel.blogPosts,
                viewModel = viewModel
            )
    }
}