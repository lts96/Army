package com.example.strongfriends.Application.Notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import com.example.strongfriends.Application.Activity.CurrentState
import com.example.strongfriends.R

interface NotificationFactory {
    companion object{
        fun create(context: Context, title:String, text:String, smallIcon:Int):Notification{ //notification 에는 채널(진동,사운드등 효과를 담당), 빌더(채널을 기반으로 빌더를 생성, 아이콘 텍스트같은거 셋팅), 매니저(채널 정보를 가진 노티피케이션을 생성!)
            //첫번째 인자는 컨텍스트,
            val CHANNELID="notification1"
            val builder= Notification.Builder(context,CHANNELID)
            val notificationChannel= NotificationChannel(CHANNELID, "강한친구", NotificationManager.IMPORTANCE_LOW)
            val manager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            builder.setContentTitle(title)
            builder.setContentText(text)
            builder.setSmallIcon(smallIcon)
            builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            builder.setAutoCancel(false)
            manager.createNotificationChannel(notificationChannel)
            val intent= Intent(context,CurrentState::class.java)
            val pIntent=PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(pIntent)
            return builder.build() //만든 노티피케이션을 반환한다!
            //따로 펜딩 인텐트 같은것은 없다.
        }
    }
}