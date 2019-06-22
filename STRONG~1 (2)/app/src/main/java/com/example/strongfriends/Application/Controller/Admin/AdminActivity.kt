package com.example.strongfriends.Application.Controller.Admin

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.strongfriends.Application.Services.MainService
import com.example.strongfriends.R

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }
}
