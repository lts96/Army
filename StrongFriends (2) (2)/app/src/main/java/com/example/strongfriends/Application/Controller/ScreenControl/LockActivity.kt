package com.example.strongfriends.Application.Controller.ScreenControl

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.strongfriends.R
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : AppCompatActivity() {
    companion object {

        fun newIntent(context: Context?) : Intent {
            return Intent(context, LockActivity::class.java)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("hsh","락액티비티 온크리에이트")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)
        if(LockService.isBroad==1) {
            button.text = "해제불가"
            button.isEnabled = false
        }
        else{
            button.text="눌러서해제"
            button.isEnabled=true
        }
        button.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        Log.d("hsh","락액티비티 온스타트")
        if(LockService.isBroad==1) {
            button.text = "해제불가"
            button.isEnabled = false
        }
        else{
            button.text="눌러서해제"
            button.isEnabled=true
        }
        button.setOnClickListener {
            finish()
        }
        super.onStart()
    }

    override fun onAttachedToWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD )
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
                            or View. SYSTEM_UI_FLAG_IMMERSIVE
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
            else{
                if(LockService.isBroad==1) {
                    Log.d("hsh", "현재 화면 포커스 아웃")
                    var intent = Intent(applicationContext, LockActivity.newIntent(applicationContext)::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    var pending = PendingIntent.getActivity(
                        applicationContext, 0, LockActivity.newIntent(
                            applicationContext
                        ), 0
                    )
                    pending.send()
                }

            }
        }
    }

    override fun onBackPressed() {
    }
    fun init(){

    }


}
