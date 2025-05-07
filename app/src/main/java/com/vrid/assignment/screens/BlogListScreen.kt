package com.vrid.assignment.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vrid.assignment.data_models.BlogPost
import com.vrid.assignment.navigation.NavigationDestination
import com.vrid.assignment.screens.components.BlogPostItem
import com.vrid.assignment.viewmodel.BlogViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogListScreen(
    navController: NavController,
    posts: List<BlogPost>,
    viewModel: BlogViewModel
) {
    val lazyListState = rememberLazyListState()
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollBarWidth = 5.dp
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        if (viewModel.blogPosts.isEmpty()) {
            viewModel.loadBlogPosts()
        }
        delay(1000)
        isRefreshing = false
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        },
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.3f))
                .drawBehind {
                    val elementHeight =
                        this.size.height / lazyListState.layoutInfo.totalItemsCount
                    val offset =
                        lazyListState.layoutInfo.visibleItemsInfo.first().index * elementHeight
                    val scrollbarHeight =
                        lazyListState.layoutInfo.visibleItemsInfo.size * elementHeight
                    drawRect(
                        color = Color.LightGray.copy(alpha = 0.5f),
                        topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                        size = Size(scrollBarWidth.toPx(), scrollbarHeight)
                    )
                }
                .padding(end = scrollBarWidth)
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
}