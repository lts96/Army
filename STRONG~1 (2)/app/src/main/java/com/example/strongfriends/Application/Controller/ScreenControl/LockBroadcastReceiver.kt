package com.example.strongfriends.Application.Controller.ScreenControl

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.example.strongfriends.Application.Activity.Login
import com.example.strongfriends.Application.Services.ControlService
import com.example.strongfriends.Application.Services.MainService

class LockBroadcastReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if ("android.permission.RECEIVE_BOOT_COMPLETED".equals(intent!!.action)) {
            var intent = Intent(context, MainService::class.java)
            var pending = PendingIntent.getService(context, 0, intent, 0)
            if(LockService.isBroad==1){
                pending.send()
                intent = Intent(context, Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                pending = PendingIntent.getActivity(context, 0, intent, 0)
                pending.send()
            }else{
                //그냥 부트커플릿
            }

            Log.d("hsh", "BroadCastReceiver : 부트컴플릿")
        } else if (intent.action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.d("hsh", "BroadCastReceiver : 스크린 온")
            if(LockService.isBroad==1){ // 통제 시간일때
                var intent = Intent(context, LockActivity.newIntent(context)::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                var pending = PendingIntent.getActivity(context, 0, LockActivity.newIntent(context), 0)
                pending.send()
            }else{
                //그냥 스크린 온
            }

            /*  intent=Intent(context, LockService::class.java) //여기서 겟 서비스 할때마다 온스타트 커맨드로 이동한다.
              pending=PendingIntent.getService(context,0,intent,0)
             pending.send()*/
        } else if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d("hsh", "BroadCastReceiver : 스크린 오프")
        } else if (intent.action.equals("com.example.strongfriends.lock.on")) {
            Log.d("hsh", "BroadCastReceiver : 락을 온 해라!")
       /*     var intent = Intent(context, LockService::class.java)*/
       /*     intent.putExtra("value", "lock_on")*/
            LockService.isBroad=1
            LockService.isScreen=1
            var intent=Intent(context,LockActivity::class.java)
            startActivity(context!!,intent,null)
    /*        context!!.startService(intent)*/
        } else if (intent.action.equals("com.example.strongfriends.lock.off")) {
            Log.d("hsh", "BradCastReceiver : 락을 오프 해라!")
            /*var intent = Intent(context, LockService::class.java)*/
            ControlService.isAlarmSet = 0
            LockService.isScreen=0
            LockService.isBroad=0
            /*intent.putExtra("value", "lock_off")*/
           /* context!!.startService(intent)*/
            //(LockActivity as MainService.UnLocking).unLocking()
            //락 오프 메세지가갔으면, 여기서 새로 실행시켜주면 바로끌 수있게 되잖아
            /*       var intent2=Intent(context,LockActivity::class.java)
                   context.startActivity(intent2)*/
        }
    }

}