package com.example.strongfriends.Application.Controller.Admin

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.app.admin.DevicePolicyManager.WIPE_RESET_PROTECTION_DATA
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.strongfriends.Application.Activity.Datas.login_body
import com.example.strongfriends.Application.Activity.Login
import com.example.strongfriends.Application.Controller.ScreenControl.LockService
import com.example.strongfriends.Application.Services.MainService
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.Application.SharedPreferences.PrefApp
import com.example.strongfriends.Network.Retrofit.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context?, intent: Intent?) {
        super.onEnabled(context, intent)
        Log.d("admin","AdminReceiver: onEnabled")
    }

    override fun onDisableRequested(context: Context?, intent: Intent?): CharSequence {
        var disableRequestedSeq="Reqeusting to disable Device Admin"
        Log.d("admin", "AdminReceiver : onDisableRequested")
        var intent = Intent(context, LockService::class.java)
        intent.putExtra("value", "lock_on")
        context!!.startService(intent)
        return disableRequestedSeq
    }

    override fun onDisabled(context: Context?, intent: Intent?) {
        Log.d("hsh","AdminReceiver onDisbled")
        val disposable = CompositeDisposable()
        disposable.add(
            ApiService.create().violation(
                Option.user_id,
                LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                Option.groupPin.toString(),
                0
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ it ->
                    //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                }) {
                    Log.d("LOG", "Login중 Error, ${it.message}")
                })

        super.onDisabled(context, intent)
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("admin","AdminReceiver : onReceive = "+intent!!.action)
        super.onReceive(context, intent)
    }

    override fun onNetworkLogsAvailable(context: Context?, intent: Intent?, batchToken: Long, networkLogsCount: Int) {
        Log.d("admin","AdminReceiver: onNetwork")
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
    }

    override fun getManager(context: Context?): DevicePolicyManager {
        return super.getManager(context)
    }


}