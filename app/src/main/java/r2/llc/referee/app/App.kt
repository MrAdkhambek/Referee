package r2.llc.referee.app

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch(Dispatchers.IO) {

            var index = 0
            while (isActive) {
                delay(5000)
                val intent = Intent(this@App, BroadcastReceiver::class.java).apply {
                    action = "MyAction"
                    putExtra("POSITION", ++index % 1000)
                }
                LocalBroadcastManager.getInstance(this@App).sendBroadcast(intent)
            }
        }
    }
}