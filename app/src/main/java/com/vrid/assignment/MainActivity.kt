package com.vrid.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vrid.assignment.navigation.MyApp
import com.vrid.assignment.network.NetworkObserver
import com.vrid.assignment.network.NetworkStatus
import com.vrid.assignment.ui.theme.VridAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val networkObserver = NetworkObserver(applicationContext)
        setContent {
            VridAssignmentTheme {
                val networkStatus by networkObserver.networkStatus.collectAsState(initial = NetworkStatus.Unavailable)
                MyApp(networkStatus = networkStatus)
            }
        }
    }
}