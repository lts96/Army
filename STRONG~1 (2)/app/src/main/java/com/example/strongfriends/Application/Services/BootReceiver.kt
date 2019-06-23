package com.example.strongfriends.Application.Services

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.strongfriends.Application.Activity.Login

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent!!.action)) {
            var intent = Intent(context, Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            var pending = PendingIntent.getActivity(context, 0, intent, 0)
            pending.send()
            Log.d("hsh", "BroadCastReceiver : 부트컴플릿")
        }
    }
}