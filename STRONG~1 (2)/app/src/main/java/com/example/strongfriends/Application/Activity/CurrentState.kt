package com.example.strongfriends.Application.Activity

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.strongfriends.Application.Activity.Datas.register_body
import com.example.strongfriends.Application.Controller.ScreenControl.LockActivity
import com.example.strongfriends.Application.Controller.ScreenControl.LockService
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.Application.SharedPreferences.PrefApp
import com.example.strongfriends.Application.fragment.SignFrag
import com.example.strongfriends.Network.Retrofit.ApiService
import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_current_state.*
import kotlinx.android.synthetic.main.fragment_sign.*

class CurrentState : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_state)
        init()

    }

    fun init() {
        val disposable = CompositeDisposable()

        currentState.setOnClickListener {
            finish()
        }

        disposable.add(
            ApiService.create().getUser(
                Option.user_id
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ it ->
                    Log.d("hsh", "CurrentStateActivity : ${it.toString()}")
                    currStateName.text="${it.name}"
                    currStateCamera.text="${it.camera}"
                    currStateGroup.text="${it.groupName}"
                    currStateId.text="${it.userId}"
                    currStatePin.text="${it.groupPin}"
                    //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                }) {
                    Log.d("LOG", "Error, ${it.message}")

                })
    }

    override fun onAttachedToWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        super.onAttachedToWindow()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (hasFocus) window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
        }
    }
}
