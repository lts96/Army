package com.example.strongfriends.Application.Controller.CameraControl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.strongfriends.Application.Notification.NotificationFactory
import com.example.strongfriends.R

class CameraService : Service(){
    override fun onBind(intent: Intent?): IBinder? {
        startForeground(1, NotificationFactory.create(applicationContext,"강한친구육군","강한친구 육군이 실행중입니다.", R.drawable.notification_template_icon_bg))
        Log.d("hsh","CameraService : 온바인드.")
        return null
    }
}