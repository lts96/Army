package com.example.strongfriends.Application.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.strongfriends.Application.Activity.Datas.login_body
import com.example.strongfriends.Application.Activity.MainActivity
import com.example.strongfriends.Application.Services.MainService
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.Application.SharedPreferences.PrefApp
import com.example.strongfriends.Network.Retrofit.ApiService

import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    interface callLoginListener {
        // TODO: Update argument type and name
        fun loginToSelect()
    }

    companion object {
        fun newInstance(): LoginFrag {
            val fragment = LoginFrag()
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListener()
    }

    fun setListener() {
        loginButton.isEnabled = false
        var loginButtonFlag = 0

        loginId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count>0){
                    if(loginPw.text.toString().length>0) loginButton.isEnabled=true
                }
                else loginButton.isEnabled=false
            }

        })

        loginPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count>0){
                    if(loginId.text.toString().length>0) loginButton.isEnabled=true
                }
                else loginButton.isEnabled=false
            }

        })

        loginButton.setOnClickListener {
            val disposable = CompositeDisposable()
            disposable.add(
                ApiService.create().login(
                    login_body(
                        loginId.text.toString(),
                        loginPw.text.toString()
                    )
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ it ->
                        Log.d("hsh", "LOGIN.activity: 로그인 요청에 대한 반환. : $it")
                        if (it.result.toString() == "LOGIN_SUCCESS") {
                            Toast.makeText(activity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            PrefApp.prefs.myPrefId=loginId.text.toString()
                            PrefApp.prefs.myPrefPw=loginPw.text.toString()
                            Option.user_id = PrefApp.prefs.myPrefId
                            //startActivity(Intent(this, MainActivity::class.java))
                            var intent = Intent(activity, MainService::class.java) // 메인서비스를 라고 명시한 intent 선언
                            intent.putExtra("key", "value")
                            activity!!.startService(intent) //메인서비스 실행11
                            activity!!.finish()
                        } else {
                            Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                        //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                    }) {
                        Log.d("LOG", "Error, ${it.message}")
                    })
        }
        loginToSelect.setOnClickListener {
            (activity as LoginFrag.callLoginListener).loginToSelect()
        }
    }
}
