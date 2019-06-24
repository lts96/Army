package com.example.strongfriends.Application.fragment


import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat.setTint
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.strongfriends.Application.Activity.Datas.register_body
import com.example.strongfriends.Application.SharedPreferences.PrefApp
import com.example.strongfriends.Network.Retrofit.ApiService

import com.example.strongfriends.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign.*

class SignFrag : Fragment() {

    lateinit var telephoneManager: TelephonyManager
    lateinit var imei: String
    lateinit var tel: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

    companion object {
        fun newInstance(): SignFrag {
            val fragment = SignFrag()
            return fragment
        }
    }

    interface callSignListener {
        // TODO: Update argument type and name
        fun signToSelect()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListener()
        telephoneManager = activity!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        getPhoneState()
    }

    fun getPhoneState() {
        var permissionArray = arrayOf(android.Manifest.permission.READ_PHONE_STATE)

        if (activity!!.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            imei = telephoneManager.imei
            tel = telephoneManager.line1Number
        }
    }

    fun setListener() {
        signButton.isEnabled = false
        signId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (signPw.text.length > 0 && signName.text.length > 0 && signId.text.length > 0 && signPwCheck.text.length > 0) {
                    if (signPw.text.toString().equals(signPwCheck.text.toString())) {
                        signPwCheck.setTextColor(Color.BLUE)
                        signButton.isEnabled = true
                        signButton.background.setTint(Color.parseColor("#CDDC39"))
                        signButton.setTextColor(Color.parseColor("#343030"))
                    } else {
                        signPwCheck.setTextColor(Color.RED)
                        signButton.isEnabled = false
                        signButton.setTextColor(Color.parseColor("#817B7B"))
                        signButton.background.setTint(Color.parseColor("#EFEFE8"))
                    }
                } else {
                    signButton.isEnabled = false
                    signButton.setTextColor(Color.parseColor("#817B7B"))
                    signButton.background.setTint(Color.parseColor("#EFEFE8"))
                }
            }
        })

        signPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (signPw.text.length > 0 && signName.text.length > 0 && signId.text.length > 0 && signPwCheck.text.length > 0) {
                    if (signPw.text.toString().equals(signPwCheck.text.toString())) {
                        signPwCheck.setTextColor(Color.BLUE)
                        signButton.isEnabled = true
                        signButton.background.setTint(Color.parseColor("#CDDC39"))
                        signButton.setTextColor(Color.parseColor("#343030"))
                    } else {
                        signPwCheck.setTextColor(Color.RED)
                        signButton.isEnabled = false
                        signButton.setTextColor(Color.parseColor("#817B7B"))
                        signButton.background.setTint(Color.parseColor("#EFEFE8"))
                    }
                } else {
                    signButton.isEnabled = false
                    signButton.setTextColor(Color.parseColor("#817B7B"))
                    signButton.background.setTint(Color.parseColor("#EFEFE8"))
                }
            }

        })

        signName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (signPw.text.length > 0 && signName.text.length > 0 && signId.text.length > 0 && signPwCheck.text.length > 0) {
                    if (signPw.text.toString().equals(signPwCheck.text.toString())) {
                        signPwCheck.setTextColor(Color.BLUE)
                        signButton.isEnabled = true
                        signButton.background.setTint(Color.parseColor("#CDDC39"))
                        signButton.setTextColor(Color.parseColor("#343030"))
                    } else {
                        signPwCheck.setTextColor(Color.RED)
                        signButton.isEnabled = false
                        signButton.setTextColor(Color.parseColor("#817B7B"))
                        signButton.background.setTint(Color.parseColor("#EFEFE8"))
                    }
                } else {
                    signButton.isEnabled = false
                    signButton.setTextColor(Color.parseColor("#817B7B"))
                    signButton.background.setTint(Color.parseColor("#EFEFE8"))
                }
            }
        })

        signPwCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (signPw.text.length > 0 && signName.text.length > 0 && signId.text.length > 0 && signPwCheck.text.length > 0) {
                    if (signPw.text.toString().equals(signPwCheck.text.toString())) {
                        signPwCheck.setTextColor(Color.BLUE)
                        signButton.isEnabled = true
                        signButton.background.setTint(Color.parseColor("#CDDC39"))
                        signButton.setTextColor(Color.parseColor("#343030"))
                    } else {
                        signPwCheck.setTextColor(Color.RED)
                        signButton.isEnabled = false
                        signButton.setTextColor(Color.parseColor("#817B7B"))
                        signButton.background.setTint(Color.parseColor("#EFEFE8"))
                    }
                } else {
                    signButton.isEnabled = false
                    signButton.setTextColor(Color.parseColor("#817B7B"))
                    signButton.background.setTint(Color.parseColor("#EFEFE8"))
                }
            }
        })

        signButton.setOnClickListener {
            val disposable = CompositeDisposable()
            disposable.add(
                ApiService.create().register(
                    register_body(
                        signId.text.toString(),
                        signPw.text.toString(),
                        tel,
                        signName.text.toString(),
                        "병장",
                        "없음",
                        "00000000000000",
                        0,
                        0,
                        0,
                        -1,
                        "USER",
                        imei
                    )
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ it ->
                        Log.d("hsh", "LOGIN.activity: 회원가입 요청에 대한 반환. : $it")
                        if (it.toString() == "REGISTER_OK") Toast.makeText(activity, "회원가입 성공", Toast.LENGTH_SHORT)
                        PrefApp.prefs.myPrefId = signId.text.toString()
                        PrefApp.prefs.myPrefPw = signPw.text.toString()
                        //여기에서 만약 옵션도 같이 왔다면, 현재 바인드 서비스가 되어있는 CottrolService 에게 알림을 보낸다.
                        Toast.makeText(activity, "회원가입 성공!", Toast.LENGTH_LONG).show()
                        (activity as SignFrag.callSignListener).signToSelect()
                    }) {
                        Log.d("LOG", "Error, ${it.message}")
                        Toast.makeText(activity, "회원가입 실패 다시 입력해주세요", Toast.LENGTH_LONG).show()
                    })
        }
        signToSelect.setOnClickListener {
            (activity as SignFrag.callSignListener).signToSelect()
        }

    }

    private fun checkAppPermission(requestPermission: Array<String>): Boolean {
        val requestResult = BooleanArray(requestPermission.size)
        for (i in requestResult.indices) {
            requestResult[i] = ContextCompat.checkSelfPermission(
                activity!!.applicationContext,
                requestPermission[i]
            ) == PackageManager.PERMISSION_GRANTED
            if (!requestResult[i]) { // 허가안될경우
                return false
            }
        }
        return true
    }


}
