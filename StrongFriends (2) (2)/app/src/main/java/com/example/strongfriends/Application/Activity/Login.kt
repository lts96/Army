package com.example.strongfriends.Application.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.strongfriends.Application.Activity.Datas.login_body
import com.example.strongfriends.Application.Activity.Datas.register_body
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.Network.Retrofit.ApiService
import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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

        loginButton.setOnClickListener {
            val disposable= CompositeDisposable()
            disposable.add(
                ApiService.create().login(
                    login_body(
                        loginId.text.toString(),
                        loginPw.text.toString()
                    )
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({it->
                        Log.d("hsh","LOGIN.activity: 로그인 요청에 대한 반환. : $it")
                        if(it.result.toString()=="LOGIN_SUCCESS") {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT)
                            Option.user_id=loginId.text.toString()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT)
                        }
                        //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                    }){
                        Log.d("LOG","Error, ${it.message}")
                    })
        }

    }
}
