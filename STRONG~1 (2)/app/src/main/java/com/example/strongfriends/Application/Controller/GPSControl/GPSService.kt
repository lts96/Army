package com.example.strongfriends.Application.Controller.GPSControl

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.Secure



class GPSService {
   /* private fun turnGPSOn() {
        val provider = Settings.Secure.getString(ContentResolver., Settings.Secure.LOCATION_PROVIDERS_ALLOWED)

        if (!provider.contains("gps")) { //if gps is disabled
            val poke = Intent()
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider")
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            sendBroadcast(poke)
        }
    }

    private fun turnGPSOff() {
        val provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED)

        if (provider.contains("gps")) { //if gps is enabled
            val poke = Intent()
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider")
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            sendBroadcast(poke)
        }
    }*/
}