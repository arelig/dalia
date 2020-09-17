package com.arelig.dalia.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationManagerCompat
import com.arelig.dalia.R


class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val builder = Builder(
            context!!, "notifyDalia"
        )
            .setSmallIcon(R.drawable.ic_dalia)
            .setContentTitle("Dalia Notify Center")
            .setContentText("Did you take your five minutes break for your babies yet?")
            .setPriority(PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }
}
