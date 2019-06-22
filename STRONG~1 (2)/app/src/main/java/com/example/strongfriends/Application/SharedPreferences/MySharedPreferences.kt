package com.example.strongfriends.Application.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
    val PREFS_FILENAME="prefs"
    val PREF_ID_KEY = "prefId"
    val PREF_PW_KEY = "prefPw"
    val prefs:SharedPreferences=context.getSharedPreferences(PREFS_FILENAME,0)

    var myPrefId:String
        get() = prefs.getString(PREF_ID_KEY,"")
        set(value) = prefs.edit().putString(PREF_ID_KEY,value).apply()

    var myPrefPw:String
        get() = prefs.getString(PREF_PW_KEY,"")
        set(value) = prefs.edit().putString(PREF_PW_KEY,value).apply()

}