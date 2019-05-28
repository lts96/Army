package com.example.strongfriends.Application.Controller.ScreenControl

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.strongfriends.Application.Services.MainService

class LockBroadcastReceiver():BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent!!.action)) {
            var intent = Intent(context, MainService::class.java)
            var pending = PendingIntent.getService(context, 0, intent, 0)
            pending.send()
            intent = Intent(context, LockActivity.newIntent(context)::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            pending = PendingIntent.getActivity(context, 0, intent, 0)
            pending.send()
            Log.d("hsh", "BroadCastReceiver : 부트컴플릿")
        } else if (intent.action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.d("hsh", "BroadCastReceiver : 스크린 온")
            var intent = Intent(context, LockActivity.newIntent(context)::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            var pending = PendingIntent.getActivity(context, 0, LockActivity.newIntent(context), 0)
            pending.send()
            /*  intent=Intent(context, LockService::class.java) //여기서 겟 서비스 할때마다 온스타트 커맨드로 이동한다.
              pending=PendingIntent.getService(context,0,intent,0)
             pending.send()*/
        } else if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d("hsh", "BroadCastReceiver : 스크린 오프")

        }
    }
}