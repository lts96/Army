package com.example.strongfriends.Application.Controller.Admin

import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.strongfriends.Application.Notification.NotificationFactory
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.R

class AdminService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object{
        var isCamera=0
        var isStart=0
        lateinit var mDevicePolicyManager: DevicePolicyManager
        lateinit var mDevicePolicyAdmin: ComponentName
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("hsh","AdminService: 어드민서비스 시작.")
        setPolicyManager()
        startForeground(1, NotificationFactory.create(applicationContext,"강한친구육군","강한친구 육군이 실행중입니다.", R.drawable.notification_template_icon_bg))
        return START_STICKY
    }

    fun setPolicyManager(){
        mDevicePolicyManager=getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        mDevicePolicyAdmin=ComponentName(applicationContext,AdminReceiver::class.java)
        val intent=Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDevicePolicyAdmin)
//        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDevicePolicyAdmin)
        //(this as AdminActivity).startActivityForResult(intent, 0)
        mDevicePolicyManager.setCameraDisabled(mDevicePolicyAdmin,true)
        //이곳을 이런식으로 할 필요가 없다.

        //추가하고싶은거잇으면 풋엑스트라로 더 추가.
    }

}