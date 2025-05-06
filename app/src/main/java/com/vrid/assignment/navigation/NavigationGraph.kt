package com.vrid.assignment.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vrid.assignment.screens.BlogDetailScreen
import com.vrid.assignment.screens.BlogListScreen
import com.vrid.assignment.screens.ErrorScreen
import com.vrid.assignment.viewmodel.BlogViewModel

@Composable
fun MyApp(
    blogViewModel: BlogViewModel
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen = currentBackStackEntry?.destination?.route

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        topBar = {
            if (!blogViewModel.isLoading) {
                TopAppBar(
                    navController = navController,
                    currentScreen = currentScreen
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = NavigationDestination.BlogList.name
        ) {
            composable(
                route = NavigationDestination.BlogList.name
            ) {
                BlogListScreen(
                    navController = navController,
                    viewModel = blogViewModel
                )
            }
            composable(
                route = "${NavigationDestination.BlogDetails.name}/{postId}",
                arguments = listOf(navArgument("postId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId")
                if (postId != null) {
                    BlogDetailScreen(
                        postId = postId,
                        viewModel = blogViewModel
                    )
                } else {
                    ErrorScreen(
                        errorMessage = "Post not found!"
                    )
                }
            }
        }
    }
}