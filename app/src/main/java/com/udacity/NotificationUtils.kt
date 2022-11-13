package com.udacity

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

// Notification ID.
private val NOTIFICATION_ID = 0
//private val REQUEST_CODE = 0
//private val FLAGS = 0

@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
    fileName: String,
    status: String
) {

//    val contentIntent = Intent(applicationContext, MainActivity::class.java)
//    val contentPendingIntent = PendingIntent.getActivity(
//        applicationContext,
//        NOTIFICATION_ID,
//        contentIntent,
//        PendingIntent.FLAG_UPDATE_CURRENT
//    )
    val detailsIntent = Intent(applicationContext, DetailActivity::class.java)
    detailsIntent.putExtra("fileName", fileName)
    detailsIntent.putExtra("status", status)
    val detailsPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        detailsIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(
        applicationContext, applicationContext.getString(
            R.string.notification_channel_id
        )
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
//        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(R.drawable.ic_assistant_black_24dp, "check the status", detailsPendingIntent)
    notify(NOTIFICATION_ID, builder.build())

}

