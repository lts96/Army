package com.example.strongfriends.Application.Services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.example.strongfriends.Application.Controller.Admin.AdminService
import com.example.strongfriends.Application.Controller.CameraControl.CameraService
import com.example.strongfriends.Application.Controller.ScreenControl.LockService
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class ControlService : Service() {

    //test
    var count=0
    //test
    companion object{
        val mServiceMessenger=Messenger(ControlHandler())


        var alarmManager:AlarmManager?=null
        var startIntent:PendingIntent?=null
        var endIntent:PendingIntent?=null

        var totalAble=0 //0이면 통제하지 않음, 1이면 통제함.
        var isAlarmSet=0
        var isLockControl= 0//1이면 통제, 0이면 통제X
        var isCameraControl =0 //1이면 통제, 0이면 통제X
        lateinit var startPending:PendingIntent
        lateinit var endPending:PendingIntent
    }

    fun getOption(){ //옵션에 있는 옵션들을 가져다가 일단 나를 초기화한다.
        totalAble=Option.enable
        Log.d("hsh","ControlService: 전체는 $totalAble")
        isCameraControl=Option.camera
        Log.d("hsh","ControlService: 카메라는 $isCameraControl")
        isLockControl=Option.screen
        Log.d("hsh","ControlService: 스크린락은 $isLockControl")

    }
    lateinit var lockMessenger:Messenger// 락이 바인드 되고 나한테 줄 메신저다.
    var isMBound=0 //메인이랑 바운드 되어있는지,
    var isLockBound=0//락이랑 바운드 되어있는지
    class ControlHandler : Handler() { //이건 내 상위 메인서비스에서 나 한테 줬을때의 핸들러
        var mActivityMessenger: Messenger?=null //이건뭐냐면 그거야 부모 서비스에서 온 것.
        override fun handleMessage(msg: Message?) { // 이 핸들러가 메세지를 핸들했을 때를 오버라이딩.

            when(msg?.what){ //msg의 what을 보고 결정하자.
                0->{ //커넥트
                    //test
                    //test
                    mActivityMessenger=msg.replyTo //커넥트네, 그러면 거기서 답장할 그새끼의 메신저를 줬겠지? replyto를 그거를 여기다가 저장한다.
                    Log.d("hsh","ControlService : 서버에서 새로운 옵션 도착, 옵션 적용 후 성공적이면 1을 반환.")
                    val value=msg.arg1 //거기서 값을 보낸대, 앞에서 보낸 12345667 였을거야.

                    try{
                        var msg=Message.obtain(null,0,1)
                        Optional.ofNullable(mActivityMessenger).ifPresent {  //그러면 트라이 하자. ㅇ액티비티메신저가 널 이 아니면,
                            t: Messenger -> t.send(msg) //mActivitymesneger, 즉 그 새끼의 메신저에다가 보내자. what은 2로 보내고 다시 값은 니가준거 그대로해서 보내.
                        }
                    } catch (e:Exception){
                        Log.d("hsh","MainToControlHandler : 값 전송 했는데 에러났을때임 ㅅㅄㅄㅂ")
                    }
                }
                1->{ //디스커넥트
                    mActivityMessenger=null //거기서 디스커넥트를 보냈다? 이건 그냥 안왔다는 소리아님? 뭐지.
                    Log.d("hsh","ControlService : Disconnect")
                }
                2->{ // 값 전송
                    Log.d("hsh","MainToControlHandler : 메인서비스에서 보낸 값 성공적 도착, 메인서비스로 값 전송,")
                }
                else->super.handleMessage(msg)
            }
        }
    }

    val lockConnection: ServiceConnection =object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) { //여기의 아이바인더란 바인딩 되는 서비스에서 리턴한 객체이다.
            Log.d("hsh","ControlService : onServiceCOnnected, 락 스크린과 연결이 되었다..")
            lockMessenger=Messenger(service) //거기서 바인더로 온 객체를 내거에 저장한다? 그러니까 여기에 보내면 거기선 핸들러로 받게 된다는 이야기겠지?
            try{
                //이제여기서 값들을 보내면 된다.
                isLockBound=1
                var msg=Message.obtain(null,0,0,0) //what은 0 으로 0 이라는 값을 보냈다.
               // msg.replyTo= MainService.mActivityMessenger
                lockMessenger?.send(msg)

            } catch(e:Exception){
                Log.d("hsh","MainService : 에서 커넥션이 되었을 때, 내가 내가 만든 서비스한테 메세지 보냄. ${e.message}")
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? { //아 이따위로 하면 안됨. 일단 시작하고 봐야됨. 아아앙잘못생각햇ㅈ.
        getOption() //일단 바인드가 되었다? 무언가 바뀌었다. 그러면 돌고있는 서비스들한테 다 갱신해라! 라고 알려줘얗나다.
        if(totalAble==1){
            if(isLockControl==1){
                setLock()
            }
            if(isCameraControl==1){
                setCamera()
            }
             //락에대한 거 세팅.

        }

        //Log.d("hsh","ControlService : OnBind")
        return mServiceMessenger!!.binder
    }





    fun setCamera(){
        startService(Intent(applicationContext, AdminService::class.java))
    }

    fun setLock(){
        var now=LocalDateTime.now().plusHours(13)
        var end=Option.getFormattedTime()[1]
        var start=Option.getFormattedTime()[0]
        Log.d("hsh","시작시간 : $start, 끝시간 $end, 현재시간 $now")
        if(isAlarmSet==0) {
            if(now.isBefore(end)&&now.isAfter(start)){
                setAlarm()
                isAlarmSet=1
                Log.d("hsh","ControlService: 스크린 Lock 기간 입니다. 알람이 세팅이 되어있지 않습니다 알람set하겠습니다. ")
            }else{
                var intent=Intent(applicationContext, LockService::class.java)
                intent.putExtra("value","start")
                applicationContext.startService(intent)
                Log.d("hsh","ControlService: 알람이 세팅되어 있지 않지만, 스크린 Lock Time 도 아닙니다. 세팅X")
            }

        }
        if(now.isBefore(end)&&now.isAfter(start)){
            Log.d("hsh","스크린Lock TIme입니다. ")
            var intent=Intent(applicationContext, LockService::class.java)
            intent.putExtra("value","lock_on")
            applicationContext.startService(intent)
        }
    }

    override fun onDestroy() {
        Log.d("hsh","ControlService는 할 일을 다 했고 이제 종료한다.")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       Log.d("hsh","ControlService : OnStartCommand")
        /*        var timer=Timer()
                   timer.schedule(object:TimerTask(){
                   override fun run() {
                       Log.d("hsh", "ControlService 의 타이머 반복중")
                   }

               },0,1000)*/

        return START_STICKY
    }

    fun setAlarm(){ //최신 옵션이 생겼을때만 컨트롤 서비스는 호출이 된다.
        //var pend=PendingIntent.getBroadcast(applicationContext,"com.example.strongfriends.lock.on")




        var intent=Intent("com.example.strongfriends.lock.on")
        var timer=Option.getFormattedTime()
        var alarmMessenger=applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager //알람 매니저 여따가 등록할 것이다.

        if(isAlarmSet==1){
            alarmMessenger.cancel(startPending)
            alarmMessenger.cancel(endPending)
            Log.d("hsh","알람 캔슬.")
        }
        // var testTime=ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()).toInstant().toEpochMilli() zoneddatetime으로 바꾼 현재 시간이다. 9시간 더해져있다.

        var testTime=ZonedDateTime.of(timer[0], ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() //서울/아시아 태그 붙였다.
      // Log.d("hsh","씨발알람 ${testTime.toInstant().toEpochMilli()},  지금 : ${ZonedDateTime.of(LocalDateTime.now(),ZoneId.of())}") //13시간 빠르다.

        var pendingIntent= PendingIntent.getBroadcast(applicationContext,0,intent, PendingIntent.FLAG_ONE_SHOT) //펜딩 인텐트는 이러하다. context에다가 코드0으로  lock.on보낼것.
        startPending=pendingIntent
        alarmMessenger.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,testTime,pendingIntent)

        testTime= ZonedDateTime.of(timer[1], ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli()
        Log.d("hsh","${ZonedDateTime.now().toInstant().toEpochMilli()}는 지금, $testTime 은 알람")
        pendingIntent= PendingIntent.getBroadcast(applicationContext,0,Intent("com.example.strongfriends.lock.off"), PendingIntent.FLAG_CANCEL_CURRENT)
        endPending=pendingIntent
        alarmMessenger.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,testTime,pendingIntent)
    }
}