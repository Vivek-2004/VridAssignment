package com.vrid.assignment.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vrid.assignment.data_models.BlogPost
import com.vrid.assignment.navigation.NavigationDestination
import com.vrid.assignment.screens.components.BlogPostItem

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(90.dp),
            strokeWidth = 8.dp
        )
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Error: $errorMessage",
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun BlogPostsList(
    navController: NavController,
    posts: List<BlogPost>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(posts) { post ->
            BlogPostItem(
                post = post,
                onClick = {
                    navController.navigate("${NavigationDestination.BlogDetails.name}/${post.id}")
                }
            )
        }
    }
}