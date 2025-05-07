package com.vrid.assignment.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.vrid.assignment.screens.components.SkeletonLoadingScreen
import com.vrid.assignment.viewmodel.BlogViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BlogDetailScreen(
    postId: Int,
    viewModel: BlogViewModel
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollState = rememberScrollState()
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }
    var post by remember { mutableStateOf(viewModel.blogPosts.firstOrNull { it.id == postId }) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        post = viewModel.blogPosts.firstOrNull { it.id == postId }
        webView?.reload()
        delay(1000)
        isRefreshing = false
    }

    if (post == null) {
        Text(
            text = "Post not found",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (isLoading) Modifier.alpha(0f) else Modifier),
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true

                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(
                                    view: WebView?,
                                    url: String?,
                                    favicon: Bitmap?
                                ) {
                                    super.onPageStarted(view, url, favicon)
                                    isLoading = true
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    isLoading = false
                                }
                            }

                            post?.let { safePost ->
                                loadUrl(safePost.link)
                            }
                            webView = this
                        }
                    },
                    update = { view ->
                        webView = view
                    }
                )
            }

            if (isLoading) {
                SkeletonLoadingScreen()
            }
        }
    }
}