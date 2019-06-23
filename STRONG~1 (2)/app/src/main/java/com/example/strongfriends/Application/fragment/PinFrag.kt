package com.example.strongfriends.Application.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.strongfriends.Application.Services.MainService
import com.example.strongfriends.Application.Services.Option
import com.example.strongfriends.Network.Retrofit.ApiService

import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_pin.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PinFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin, container, false)
    }

    companion object {
        fun newInstance(): PinFrag {
            val fragment = PinFrag()
            return fragment
        }
    }

    interface callPinListener {
        fun changeToPin()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setListener()
        super.onActivityCreated(savedInstanceState)
    }

    fun setListener(){
        pinButton.isEnabled=false

        pinEdit.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(pinEdit.text.length==6){
                    pinButton.isEnabled=true
                    pinButton.background.setTint(Color.parseColor("#CDDC39"))
                    pinButton.setTextColor(Color.parseColor("#343030"))
                    Log.d("hsh","핀번호는6이어야함. ${pinEdit.text.length}")

                }else{
                    pinButton.isEnabled=false
                    pinButton.setTextColor(Color.parseColor("#817B7B"))
                    pinButton.background.setTint(Color.parseColor("#EFEFE8"))
                    Log.d("hsh","핀번호는. ${pinEdit.text.length}")

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        pinButton.setOnClickListener {
            val disposable = CompositeDisposable()
            disposable.add(

                ApiService.create().gotoRoom(
                    Option.user_id,
                   pinEdit.text.toString().toInt()
                ).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ it ->
                        //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                        if(it.pin!=0){
                            Toast.makeText(activity,"그룹 입장 성공!",Toast.LENGTH_LONG).show()
                            Option.groupPin=it.pin
                            var intent = Intent(activity, MainService::class.java) // 메인서비스를 라고 명시한 intent 선언
                            intent.putExtra("key", "value")
                            activity!!.startService(intent) //메인서비스 실행11
                            activity!!.finish()
                        }else{
                            pinEdit.text.clear()
                            pinEdit.hint="잘못된 PIN 번호 입니다."

                        }

                    }) {
                        Log.d("LOG", "Login중 Error, ${it.message}")
                    })
        }
    }

}
