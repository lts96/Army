package com.example.strongfriends.Application.Controller.ScreenControl

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.example.strongfriends.Application.Notification.NotificationFactory
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.R

class LockService: Service() {
    companion object{
        var broadcast:LockBroadcastReceiver=LockBroadcastReceiver() //락 온이랑 락 오프라는 브로드캐스트 리시버 (알람이 울리면 그걸 메인에서 받고 이걸 켜준다는 것이다.)
        var isStart=0 //스타트 포그라운드...
        var isBroad=0 //현재 스크린 락을 하고있는가.
        var isScreen=0 //현재 화면이 켜져있는가?
    }
    override fun onBind(intent: Intent?): IBinder? {

        Log.d("hsh","LockService : 온바인드. 아마 쓰일일은 없을 것이다.")
        return mMessenger.binder
    }

    private fun initLockScreen(){

    }

    var mMessenger= Messenger(object: Handler() { //쟤랑 내가 동시에 가지고있을 인터페이스는 메신저인데, 메신저의 안에는 핸들러가 들어간다.
        override fun handleMessage(msg: Message?) {
            //Log.d("LOG","내가받은 what은 {${msg?.what}}") //일단 옵션을 받아왔다는 자체가 옵션이 이전것이기 때문에 나는 지금 갱신을 해야한다! 라는 소리이다.
            if(msg?.what==4){ //뭐 이게 새로운 옵션이 왔을때 라고해보자.
                Log.d("LOG","메인서비스가 새로운 옵션을 받아와서 나한테 메세지를 줬다!")
                //그렇다면 이미 옵션은 새로 새로고침이 되어있으니까 나는 그 옵션들로 다시 적용만 시키면 된다!
            }

            // 그리고 여기다가, 각각 내가 제어하는 서비스에서
            super.handleMessage(msg)
        }
    })

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //startService에서 여기로 넘어온다.
        Log.d("hsh","LockService : startService,$isStart}")
        if(isStart==0) //한번 실행되었던 놈이면 startForeground를 재실행 시키지는 않는다.
            startForeground(1, NotificationFactory.create(applicationContext,"강한친구육군","세부사항을 보려면 클릭하세요.", R.drawable.notification_template_icon_bg))
        isStart=1
      /*  if(broadcast==null){
            ScreenLock()
        }*/
        ScreenLock()
       /* if(intent!=null) {
            if(intent!!.extras["value"].equals("lock_on")){ //시간이 되었다 락 해라.
                if(isBroad!=1){ //이미 브로드 캐스트 리시버가 작동중이지 않으면,
                    ScreenLock() // 레지스트 리시버
                    Log.d("hsh","LockService의 락 온")
                    var intent=Intent(applicationContext,LockActivity::class.java)
                    startActivity(intent)
                }
            }
            else if(intent.extras["value"].equals("lock_off")){ //시간이 끝났다ㅣ 락 풀어라.
                if(isBroad==1){
                    unregisterReceiver(broadcast)
                    isBroad=0
                }
            }
            else if(intent.extras["value"].equals("start")){

            }
        }*/
        return START_STICKY
    }

    override fun onDestroy() {
        Log.d("hsh","LockService::종료합니다.")
        super.onDestroy()
    }

    fun ScreenLock() {
        Log.d("hsh","스크린락")
        var filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_BOOT_COMPLETED)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction("com.example.strongfriends.lock.off")
        filter.addAction("com.example.strongfriends.lock.on")
        broadcast = LockBroadcastReceiver()
        registerReceiver(broadcast, filter)
    }
}