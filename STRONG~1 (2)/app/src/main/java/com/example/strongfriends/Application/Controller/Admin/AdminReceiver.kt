package com.example.strongfriends.Application.Controller.Admin

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.strongfriends.Application.Controller.ScreenControl.LockService

class AdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context?, intent: Intent?) {
        super.onEnabled(context, intent)
        Log.d("admin","AdminReceiver: onEnabled")

    }

    override fun onDisableRequested(context: Context?, intent: Intent?): CharSequence {
        var disableRequestedSeq="Reqeusting to disable Device Admin"
        Log.d("admin", "AdminReceiver : onDisableRequested")
        var intent = Intent(context, LockService::class.java)
        intent.putExtra("value", "lock_on")
        context!!.startService(intent)
        return disableRequestedSeq
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("admin","AdminReceiver : onReceive = "+intent!!.action)
        super.onReceive(context, intent)
    }

    override fun onNetworkLogsAvailable(context: Context?, intent: Intent?, batchToken: Long, networkLogsCount: Int) {
        Log.d("admin","AdminReceiver: onNetwork")
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
    }

    override fun getManager(context: Context?): DevicePolicyManager {
        return super.getManager(context)
    }


}