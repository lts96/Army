package com.example.strongfriends.Application.Activity

import android.app.admin.DevicePolicyManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.strongfriends.Application.Activity.Datas.login_body
import com.example.strongfriends.Application.Activity.Datas.register_body
import com.example.strongfriends.Application.Controller.Admin.AdminReceiver
import com.example.strongfriends.Application.Controller.Admin.AdminService
import com.example.strongfriends.Application.Services.MainService
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.Application.SharedPreferences.PrefApp
import com.example.strongfriends.Application.fragment.LoginFrag
import com.example.strongfriends.Application.fragment.PinFrag
import com.example.strongfriends.Application.fragment.SelectFrag
import com.example.strongfriends.Application.fragment.SignFrag
import com.example.strongfriends.Network.Retrofit.ApiService
import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.*
import android.app.Activity
import android.app.admin.DeviceAdminReceiver
import android.content.*


class Login : AppCompatActivity(), LoginFrag.callLoginListener, SelectFrag.callSelectListener,
    SignFrag.callSignListener,PinFrag.callPinListener {

    lateinit var pref:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    var permissionArray = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_PHONE_NUMBERS,
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.RECEIVE_BOOT_COMPLETED

    )
    var REQUEST_PERMISSION = 10

    companion object {
        lateinit var broad:AdminReceiver
        var isBroad=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.strongfriends.R.layout.activity_login)
        setAdmin() //어드민 권한을 받아온다.
        setSharedPref() //셰어드 프리퍼런스 셋
        initPermission() //권한들 다 받아온다.
        makeSelect()
/*
        registerButton.setOnClickListener {
            val disposable= CompositeDisposable()
            disposable.add(
                ApiService.create().register(register_body(id.text.toString(),pw.text.toString(),IMEI.text.toString(),phone.text.toString(),"201정비중대","USER",name.text.toString()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({it->
                        Log.d("hsh","LOGIN.activity: 회원가입 요청에 대한 반환. : $it")
                        if(it.toString()=="REGISTER_OK") Toast.makeText(this,"회원가입 성공",Toast.LENGTH_SHORT)
                        //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                    }){
                        Log.d("LOG","Error, ${it.message}")
                    })
        }
   */
    }


    fun setSharedPref() {
        //여기서 저장된 아이디가 있는지 확인하는 역할을 한다.
        if(PrefApp.prefs.myPrefId.length!=0){ //뭔가 값이 있다면?
            var id=PrefApp.prefs.myPrefId
            var pw=PrefApp.prefs.myPrefPw
            Log.d("hsh","시발 아이디는 $id, 비밀번호는 $pw 길이${id.length} ")
            val disposable = CompositeDisposable()
            disposable.add(
                ApiService.create().login(
                    login_body(
                        id,
                        pw
                    )
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ it ->
                        Log.d("hsh", "LOGIN.activity: 로그인 요청에 대한 반환. : $it")
                        if (it.result.toString() == "LOGIN_SUCCESS") {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                            Option.user_id = PrefApp.prefs.myPrefId
                            //startActivity(Intent(this, MainActivity::class.java))
                            var intent = Intent(applicationContext, MainService::class.java) // 메인서비스를 라고 명시한 intent 선언
                            intent.putExtra("key", "value")
                            startService(intent) //메인서비스 실행
                            this!!.finish()
                        } else {
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                        //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                    }) {
                        Log.d("LOG", "Login중 Error, ${it.message}")
                    })
        }else{
            Log.d("hsh","저장된 섀어드 프리퍼런스가 없읍니다~")
        }
    }

    fun setAdmin() {
        var intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        AdminService.mDevicePolicyManager =getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        AdminService.mDevicePolicyAdmin = ComponentName(applicationContext, AdminReceiver::class.java)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, AdminService.mDevicePolicyAdmin)
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
        Log.d("hsh","Login:onAcitivtyResult OK")
        }else{
            Log.d("hsh","${resultCode.toString()}")
        }
    }

    //프래그먼트 관련
    override fun loginToSelect() {
        makeSelect()
    }

    override fun selectToLogin() {
        makeLogin()
    }

    override fun selectToSign() {
        makeSign()
    }

    override fun signToSelect() {
        makeSelect()
    }

    override fun changeToPin() {
        makePin()
    }

    fun makePin(){
        var transaction=supportFragmentManager.beginTransaction()
        var pinFrag=PinFrag.newInstance()
        transaction.replace(com.example.strongfriends.R.id.frame,pinFrag,"pinFrag")
        var coommit=transaction.commit()
    }

    fun makeSign() {
        var fragment=supportFragmentManager.findFragmentByTag("signFrag")
        var transaction = supportFragmentManager.beginTransaction()
        var signFrag = SignFrag.newInstance()
        transaction.replace(com.example.strongfriends.R.id.frame, signFrag, "signFrag")
        var commit = transaction.commit()
    }

    fun makeLogin() {
        var fragment=supportFragmentManager.findFragmentByTag("loginFrag")
        if (fragment == null) { //프래그먼트가 뭔가 부착되어있는데 나는 아님.
            var transaction = supportFragmentManager.beginTransaction()
            var loginFrag = LoginFrag.newInstance()
            transaction.replace(com.example.strongfriends.R.id.frame, loginFrag, "loginFrag")
            var commit = transaction.commit()
        }
    }

    fun makeSelect() {
        var fragment = supportFragmentManager.findFragmentById(com.example.strongfriends.R.id.frame)
        if (fragment == null) { //아직 아무것도 없으면,
            fragment = SelectFrag()
            var transaction = supportFragmentManager.beginTransaction()
            var selectFrag = SelectFrag.newInstance()
            transaction.replace(com.example.strongfriends.R.id.frame, selectFrag, "selectFrag")
            var commit = transaction.commit()
        } else {
            val fragment = supportFragmentManager.findFragmentByTag("selectFrag")
            if (fragment == null) { //프래그먼트가 뭔가 부착되어있는데 나는 아님.
                var transaction = supportFragmentManager.beginTransaction()
                var selectFrag = SelectFrag.newInstance()
                transaction.replace(com.example.strongfriends.R.id.frame, selectFrag, "selectFrag")
                var commit = transaction.commit()
            } else {
                //(fragment as ListFrag).setRecyclerView( ) //나면 아무것도안하지.
            }
        }
    }




    //프래그먼트 끝

    //권한
    fun initPermission() {
        if (!checkAppPermission(permissionArray)) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("반드시 권한이 허용되어야 합니다")
                .setTitle("권한 허용")
                .setIcon(com.example.strongfriends.R.drawable.abc_ic_star_black_48dp)
            builder.setPositiveButton("OK") { _, _ ->
                askPermission(permissionArray, REQUEST_PERMISSION);
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            Toast.makeText(
                getApplicationContext(),
                "권한이 승인되었습니다.", Toast.LENGTH_SHORT
            ).show();
        }
    }

    fun checkAppPermission(requestPermission: Array<String>): Boolean {
        val requestResult = BooleanArray(requestPermission.size)
        for (i in requestResult.indices) {
            requestResult[i] = ContextCompat.checkSelfPermission(
                this,
                requestPermission[i]
            ) == PackageManager.PERMISSION_GRANTED
            if (!requestResult[i]) {
                return false
            }
        }
        return true
    } // checkAppPermission

    fun askPermission(requestPermission: Array<String>, REQ_PERMISSION: Int) {
        ActivityCompat.requestPermissions(
            this, requestPermission,
            REQ_PERMISSION
        )
    } // askPermission

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> if (checkAppPermission(permissions)) { //퍼미션 동의했을 때 할 일
                Toast.makeText(this, "권한이 승인됨", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "권한이 거절됨", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    } // onRequestPermissionsResult


}
