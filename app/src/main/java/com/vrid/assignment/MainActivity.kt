package com.vrid.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vrid.assignment.navigation.MyApp
import com.vrid.assignment.ui.theme.VridAssignmentTheme
import com.vrid.assignment.viewmodel.BlogViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VridAssignmentTheme {
                val viewModel: BlogViewModel = viewModel()
                MyApp(
                    blogViewModel = viewModel
                )
            }
        }
    }
}