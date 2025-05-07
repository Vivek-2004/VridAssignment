package com.vrid.assignment.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class NetworkStatus {
    object Unknown : NetworkStatus()
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

class NetworkObserver(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkStatus: Flow<NetworkStatus> = callbackFlow {
        trySend(NetworkStatus.Unknown)

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkStatus.Available)
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus.Unavailable)
            }

            override fun onUnavailable() {
                trySend(NetworkStatus.Unavailable)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        val isConnected = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        trySend(if (isConnected) NetworkStatus.Available else NetworkStatus.Unavailable)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}