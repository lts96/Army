package com.example.strongfriends.Application.Services

import android.app.PendingIntent
import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.*
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.example.strongfriends.Application.Activity.Datas.Periodic_body
import com.example.strongfriends.Application.Activity.Datas.Periodic_response
import com.example.strongfriends.Application.Controller.Admin.AdminReceiver
import com.example.strongfriends.Application.Controller.ScreenControl.LockService
import com.example.strongfriends.Application.Notification.NotificationFactory
import com.example.strongfriends.Network.Retrofit.ApiService
import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.example.strongfriends.Application.Activity.Login
import com.example.strongfriends.Application.Controller.Admin.AdminActivity
import com.example.strongfriends.Application.Controller.Admin.AdminService.Companion.mDevicePolicyAdmin
import com.example.strongfriends.Application.Controller.Admin.AdminService.Companion.mDevicePolicyManager
import com.example.strongfriends.Application.Controller.ScreenControl.LockActivity


class MainService : Service() {
    interface UnLocking {
        fun unLocking()
    }

    var connection: ServiceConnection? = null //binding할 controlService와의 커넥션
    var messenger: Messenger? = null //바인더가 감싸고있는 메신저 객체 여기다 보내면 .controlService의 핸들러가 받는다.
    var controlIntent: Intent? = null //컨트롤 서비스가 담길 인텐트.
    val apiService: ApiService by lazy { ApiService.create() } //lazy는 val!
    var mMessneger: Messenger? = null //내가만든게 아닌 메신저를 담을 공간.
    var mIsBound: Boolean = false //현재 바운드가 되어있냐?

    val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) { //여기의 아이바인더란 바인딩 되는 서비스에서 리턴한 객체이다.
            Log.d("hsh", "MainService : onServiceConnected, 바인드된 서비스가 반환한 값을 받았다.")
            mMessneger = Messenger(service) //거기서 바인더로 온 객체를 내거에 저장한다? 그러니까 여기에 보내면 거기선 핸들러로 받게 된다는 이야기겠지?
            try {
                //이제여기서 값들을 보내면 된다.
                var msg = Message.obtain(null, 0, 0, 0) //what은 0 으로 0 이라는 값을 보냈다.
                msg.replyTo = mActivityMessenger
                mMessneger?.send(msg)

            } catch (e: Exception) {
                Log.d("hsh", "MainService : 에서 커넥션이 되었을 때, 내가 내가 만든 서비스한테 메세지 보냄. ${e.message}")
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        var filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_BOOT_COMPLETED)
        filter.addAction("com.example.strongfriends.lock.on")
        filter.addAction("com.example.strongfriends.lock.off")
        this.registerReceiver(broad, filter)

        Log.d("hsh", "MainService : OnCreate 서비스가 생성될 떄 단 한번만 실행 된다고 한다!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("hsh", "MainService : OnStartCommand")
        Log.d("hsh", "메인액티비티에서 넘어온 값은 ${intent!!.extras["key"].toString()}")
        var bitmap = BitmapFactory.decodeResource(this.resources, com.example.strongfriends.R.mipmap.ic_launcher)
        startForeground(
            1,
            NotificationFactory.create(this, "강한친구육군", "메인서비스", com.example.strongfriends.R.mipmap.ic_launcher)
        )
        var timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // Log.d("hsh","MainService onStartCommand 에서 periodic쿼리를 전송.")
                //여기서 쿼리 전송. 답이 왔을 때 옵션이 있다면 실행시킬 함수 구현해야함.
                //sendQuery()
                sendQuery()
                if (isOptionUpdate == 1) {
                    unbindService(mConnection)
                    isOptionUpdate = 0
                }
                /*   if(Random().nextInt(4)==3){
                       var intent=Intent(applicationContext,ControlService::class.java)
                       //startService(intent) 시작할때마다 인텐트를 받는다는것을 확인하였다.
                       mIsBound=bindService(Intent(applicationContext,ControlService::class.java),mConnection, Context.BIND_AUTO_CREATE) //바인드 시작
                       Log.d("hsh","MainService 쿼리에 대한 답장으로 새 옵션이 도착.")
                   }*/
            }
        }, 0, 10000)
        return START_STICKY
    }

    fun sendQuery() { //정기적으로 보내는 쿼리이다.
        //쿼리보내기
        val disposable = CompositeDisposable()
        disposable.add(
            apiService.periodicQuery(Periodic_body(Option.user_id, Option.lastUpdate.toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ it ->
                    Log.d("hsh", "MainService : fun sendQuery : 성공!")
                    //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                    if (it.isLastOption == 0) { //최신 옵션이라면?
                        mIsBound = bindService(
                            Intent(applicationContext, ControlService::class.java),
                            mConnection,
                            Context.BIND_AUTO_CREATE
                        ) //서비스를 바인드한다.
                        Log.d("hsh", "MainService 쿼리에 대한 답장으로 새 옵션이 도착.")
                        setOption(it)
                    }
                }) {
                    Log.d("hsh", "MainService : fun sendQuery : 실패")
                })
    }

    fun setOption(option: Periodic_response) {
        Option.enable = option.enable
        Option.camera = option.camera
        Option.screen = option.screen
        //Option.timer=option.control_time
        Option.timer = "2019052813000020190623142600"
        Option.lastUpdate = option.last_update.toString()
    }

    class myHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                0 -> { //이건 뭐냐? 그거다.
                    //Log.d("hsh","MainService : Control Service는 성공적으로 옵션을 적용했다. 이제 나랑 언바인드 해도 된다.")
                    //var option:Array<String> = msg.obj as Array<String>
                    //Log.d("hsh","${option.get(1)}, ${option.get(0)}, ${option.get(2)}")
                    isOptionUpdate = 1 //옵션이 새로 업데이트 됐다. 언바인드 할 차례이다.ㅇ
                }
                else -> super.handleMessage(msg)
            }
            super.handleMessage(msg)
        }
    }

    companion object {
        val mActivityMessenger: Messenger =
            Messenger(myHandler()) //메신전데 내가 만든 핸들러를 담은 거야. 내가 밑에서 메세지 보낼거지? 거기에 대한 답은 여기에 하라고.

        var isOptionUpdate: Int = 0
        var broad: BootReceiver = BootReceiver()
    }

    class BootReceiver : BroadcastReceiver() { //브로드캐스터 리시버
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action.equals("com.example.strongfriends.lock.on")) {
                Log.d("hsh", "BroadCastReceiver : 락을 온 해라!")
                var intent = Intent(context, LockService::class.java)
                intent.putExtra("value", "lock_on")
                context.startService(intent)
            } else if (intent.action.equals("com.example.strongfriends.lock.off")) {
                Log.d("hsh", "BradCastReceiver : 락을 오프 해라!")
                var intent = Intent(context, LockService::class.java)
                ControlService.isAlarmSet = 0
                intent.putExtra("value", "lock_off")
                context.startService(intent)
                //(LockActivity as MainService.UnLocking).unLocking()
                //락 오프 메세지가갔으면, 여기서 새로 실행시켜주면 바로끌 수있게 되잖아
                /*       var intent2=Intent(context,LockActivity::class.java)
                       context.startActivity(intent2)*/
            } else if (intent!!.action.equals("android.intent.action.BOOT_COMPLETED")) {
                var intent = Intent(context, Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                var pending = PendingIntent.getActivity(context, 0, intent, 0)
                pending.send()
                Log.d("hsh", "BroadCastReceiver : 부트컴플릿")
            }
        }
    }

    fun setBootReceiver() {

    }


}