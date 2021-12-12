package r2.llc.referee.listeners

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NetworkLiveData @Inject constructor(
    @ApplicationContext private val context: Context,
) : LiveData<Boolean>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    init {
        val connectManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) connectManager.registerDefaultNetworkCallback(networkCallback)
        else {
            val builder = NetworkRequest.Builder()
            connectManager.registerNetworkCallback(builder.build(), networkCallback)
        }


        var isConnected = false
        connectManager.allNetworks.forEach { network ->
            connectManager.getNetworkCapabilities(network)?.let { capabilities ->
                isConnected = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (isConnected) return@forEach
            }
        }

        postValue(isConnected)
    }

    override fun onInactive() {
        val connectManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectManager.unregisterNetworkCallback(networkCallback)
        super.onInactive()
    }
}

class ListenerUtil @Inject constructor(
    @ApplicationContext private val context: Context,
)  {

    fun networkFlow(): Flow<Boolean> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true).isSuccess
            }

            override fun onLost(network: Network) {
                trySend(false).isSuccess
            }
        }


        val connectManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) connectManager.registerDefaultNetworkCallback(networkCallback)
        else {
            val builder = NetworkRequest.Builder()
            connectManager.registerNetworkCallback(builder.build(), networkCallback)
        }


        var isConnected = false
        connectManager.allNetworks.forEach { network ->
            connectManager.getNetworkCapabilities(network)?.let { capabilities ->
                isConnected = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (isConnected) return@forEach
            }
        }

        trySend(isConnected).isSuccess

        awaitClose {
            connectManager.unregisterNetworkCallback(networkCallback)
        }
    }
}