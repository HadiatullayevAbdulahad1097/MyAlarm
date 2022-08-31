package com.abdulahad.myalarm.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log

class OnTimeReceiver : BroadcastReceiver() {
    private val TAG = "OnTimeReceiver"
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ")
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val r = RingtoneManager.getRingtone(context,
            notification)
        r.play()
    }
}