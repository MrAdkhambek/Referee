package r2.llc.referee.listeners

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class BroadcastLiveData @Inject constructor(
    @ApplicationContext private val context: Context,
) : LiveData<Int>() {

    private var receiver: BroadcastReceiver? = null

    init {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.i("MyReceiver", "MyAction received!")
                intent ?: return
                val position = intent.extras?.getInt("POSITION") ?: return
                postValue(position)
            }
        }
        LocalBroadcastManager
            .getInstance(context)
            .registerReceiver(requireNotNull(receiver), IntentFilter("MyAction"))
    }

    override fun onInactive() {
        try {
            receiver?.let(LocalBroadcastManager.getInstance(context)::unregisterReceiver)
        } finally {
            receiver = null
        }
        super.onInactive()
    }
}