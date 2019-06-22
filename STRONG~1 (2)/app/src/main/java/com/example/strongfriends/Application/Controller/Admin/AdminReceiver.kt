package com.example.strongfriends.Application.Controller.Admin

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context?, intent: Intent?) {
        super.onEnabled(context, intent)
        Toast.makeText(context,"Device Admin 이 활성화 되었습니다.",Toast.LENGTH_LONG).show()
    }
    override fun onDisableRequested(context: Context?, intent: Intent?): CharSequence {
        var disableRequestedSeq="Reqeusting to disable Device Admin"
        return disableRequestedSeq
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("LOG","MyDevicePolicyReceiver Received: " + intent!!.action)
        super.onReceive(context, intent)
    }

    override fun onNetworkLogsAvailable(context: Context?, intent: Intent?, batchToken: Long, networkLogsCount: Int) {
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
    }

    override fun getManager(context: Context?): DevicePolicyManager {
        return super.getManager(context)
    }
}