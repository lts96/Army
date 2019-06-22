package com.example.strongfriends.Application.SharedPreferences

import android.app.Application

class PrefApp : Application() {
    companion object{
        lateinit var prefs:MySharedPreferences
    }

    override fun onCreate() {
        prefs=MySharedPreferences(applicationContext)
        super.onCreate()
    }

}