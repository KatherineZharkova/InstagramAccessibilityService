package com.key_key.instagramaccessibilityservice

import android.app.Activity
import android.content.Intent
import android.net.Uri


class InstagramLoader(private val activity: Activity) {
    private val instagramPackage = activity.getString(R.string.instagram_package)

    fun start() {
        with(activity){
            packageManager.getLaunchIntentForPackage(instagramPackage)?.apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(this)
            }
        }
    }

}
