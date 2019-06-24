package com.example.strongfriends.Application.Controller.Admin

import android.app.Service
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import com.example.strongfriends.Application.Activity.Login
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
        var isAdmin=0
        lateinit var mDevicePolicyManager: DevicePolicyManager
        lateinit var mDevicePolicyAdmin: ComponentName
        lateinit var adminReceivere:AdminReceiver

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("hsh","AdminService: onStartCommand.")
        setPolicyManager()
        Log.d("hsh","AdminService: 여기니.")
        startForeground(1, NotificationFactory.create(applicationContext,"강한친구육군","세부사항을 보려면 클릭하세요.", R.drawable.notification_template_icon_bg))
        Log.d("hsh","AdminService: 여기?.")
        return START_NOT_STICKY
    }

    fun setPolicyManager(){
      // mDevicePolicyManager=getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        //mDevicePolicyAdmin=ComponentName(applicationContext,AdminReceiver::class.java)
        //val intent=Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        //intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDevicePolicyAdmin)
//        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDevicePolicyAdmin)
        //(this as AdminActivity).startActivityForResult(intent, 0)
        if(Option.camera==1) {
            mDevicePolicyManager.setCameraDisabled(mDevicePolicyAdmin, true)
        }else{
            mDevicePolicyManager.setCameraDisabled(mDevicePolicyAdmin, false)
        }
        adminReceivere=AdminReceiver()
        var filter = IntentFilter(DeviceAdminReceiver.ACTION_DEVICE_ADMIN_DISABLE_REQUESTED)
        filter.addAction(DeviceAdminReceiver.EXTRA_DISABLE_WARNING)
        filter.addAction(DeviceAdminReceiver.ACTION_DEVICE_ADMIN_ENABLED)
        filter.addAction(DeviceAdminReceiver.ACTION_DEVICE_ADMIN_DISABLED)
        registerReceiver(adminReceivere,filter)
        isAdmin=1
        //mDevicePolicyManager.
        //이곳을 이런식으로 할 필요가 없다.
        //추가하고싶은거잇으면 풋엑스트라로 더 추가.
    }

    override fun onDestroy() {
        if(isAdmin==1)unregisterReceiver(adminReceivere)
        Log.d("hsh","Adminserservice : onDestroy.")
        val intent=Intent(applicationContext,Login::class.java)
        startActivity(intent)

        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d("hsh","재시작 가능.")
        super.onTaskRemoved(rootIntent)
    }
}