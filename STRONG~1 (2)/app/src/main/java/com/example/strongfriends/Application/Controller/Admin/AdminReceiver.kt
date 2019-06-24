package com.example.strongfriends.Application.Controller.Admin

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.app.admin.DevicePolicyManager.WIPE_RESET_PROTECTION_DATA
import android.content.Context
import android.content.Intent
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.example.strongfriends.Application.Activity.Datas.Log_body
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
import com.androidnetworking.error.ANError
import com.androidnetworking.gsonparserfactory.GsonParserFactory
import org.json.JSONObject
import com.androidnetworking.interfaces.JSONObjectRequestListener
import io.reactivex.internal.util.HalfSerializer.onError
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class AdminReceiver : DeviceAdminReceiver() {

    companion object {
        var json=JSONObject()

        val disposable = CompositeDisposable()
    }
    override fun onEnabled(context: Context?, intent: Intent?) {
        super.onEnabled(context, intent)
        AndroidNetworking.initialize(context)
        json.put("userId","bbhhss03")
        json.put("occurTime","1234")
        json.put("groupPin","123456")
        json.put("violation","1")
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
        //AndroidNetworking.initialize(context)
      //  var okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
       // AndroidNetworking.initialize(context,okHttpClient)
        AndroidNetworking.setParserFactory(GsonParserFactory())
        AndroidNetworking.post("http://221.155.56.120:8080/main/updateLog")
            .addJSONObjectBody(json)
            .setPriority(Priority.IMMEDIATE)
            .build()
        /*disposable.add(
            ApiService.create().violation(
                Log_body(Option.user_id,LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                    Option.groupPin,
                    1)
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ it ->
                    //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                    Log.d("hsh","어드민 리시버 잘 왔음.")
                }) {
                    Log.d("LOG", "어드민 리시버 잘못된 그거임.")
                })
*/
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