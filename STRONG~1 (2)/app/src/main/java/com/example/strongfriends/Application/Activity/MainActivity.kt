package com.example.strongfriends.Application.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.strongfriends.Application.Services.MainService


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.strongfriends.R.layout.activity_main)
      /*  var loginIntent=Intent(applicationContext,Login::class.java)
        startActivity(loginIntent)*/
        Log.d("hsh", "MainActivity : onCreate")



        var intent = Intent(applicationContext, MainService::class.java) // 메인서비스를 라고 명시한 intent 선언
        intent.putExtra("key", "value")
        startService(intent) //메인서비스 실행
        finish()
        /*intent=Intent(this,ControlService::class.java)
        startService(intent)*/
        //test

    }



    override fun onDestroy() {
        //unregisterReceiver(broad)
        super.onDestroy()
    }
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
