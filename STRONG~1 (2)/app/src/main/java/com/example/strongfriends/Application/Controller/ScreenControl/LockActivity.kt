package com.example.strongfriends.Application.Controller.ScreenControl

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
import com.example.strongfriends.Application.Services.MainService
import com.example.strongfriends.R
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : AppCompatActivity(){



    companion object {

        fun newIntent(context: Context?) : Intent {
            return Intent(context, LockActivity::class.java)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
        }
        lateinit var lockActivityBroad:LockBroad
        var isBroad=0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("hsh","락액티비티 온크리에이트")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)


        var filter = IntentFilter("com.example.strongfriends.lock.off")
        lockActivityBroad=LockBroad()

       if(isBroad==0){ //브로드가 0이면?
           Log.d("hsh","LockActivity: 브로드캐스트 리시버 붙인다.")
           registerReceiver(lockActivityBroad, filter) //레지스터 등록시킨다 이게 왜 필요하지.
           isBroad=1
       }else{
           Log.d("hsh","LockActivity: 브로드캐스트 리시버 이미있따.")
       }
    }

/*    class broad(): BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.action.equals("lock_off")){
                button.text="눌러서해제" //여기서 말고 이 내용을 브로드 캐스트 리시버 하나 등록해놓고 받아서 해볼까?
                button.isEnabled=true
            }
        }

    }*/


    override fun onStart() {
        Log.d("hsh","락액티비티 온스타트")

        if(LockService.isBroad==1) {

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
                    for(i in 0 until 30) pending.send()
                }
            }
        }
    }

    override fun onBackPressed() {
    }
    fun init(){

    }

    override fun onDestroy() {
        //unregisterReceiver(lockActivityBroad)
        super.onDestroy()
    }

     inner class LockBroad():BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.action.equals("com.example.strongfriends.lock.off")){
                Log.d("hsh","됐나?")
               // unregisterReceiver(lockBroad())

                unregisterReceiver(LockActivity.lockActivityBroad)
                finish()
                //startActivityForResult(this@LockActivity,Intent(context,LockActivity::class.java),18,null)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("hsh","LockActivity: onAcitivtyResult$requestCode ")
        if(requestCode==18){

            Log.d("hsh","${lockActivityBroad.toString()}")
           // unregisterReceiver(lockActivityBroad)
            Log.d("hsh","LockActivity: 뭐가 문제야씨발")
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
 /*   inner class UnLock{
        fun unlcok(){
            button.text="눌러서해제"
            button.isEnabled=true
        }
    }

    class lockBroad:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent!!.action.equals("com.example.strongfriends.lock.off")){
                LockActivity.UnLock.



            }
        }

    }
*/

}
