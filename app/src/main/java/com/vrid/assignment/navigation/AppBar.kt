package com.vrid.assignment.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TopAppBar(
    navController: NavController,
    currentScreen: String?
) {
    val title = when (currentScreen) {
        "BlogList" -> "Blogs"
        "BlogDetails/{postId}" -> "Details"
        else -> "Welcome"
    }

    var showNavigationIcon by remember { mutableStateOf(false) }
    showNavigationIcon = (title == "Details")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        if (showNavigationIcon) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(vertical = 6.dp),
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(
                    vertical = 12.dp,
                    horizontal = if (showNavigationIcon) 64.dp else 24.dp
                ),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textAlign = TextAlign.Left,
            fontFamily = FontFamily.Default
        )
    }
}