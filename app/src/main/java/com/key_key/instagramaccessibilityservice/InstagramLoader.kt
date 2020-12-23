package com.key_key.instagramaccessibilityservice

import android.content.Intent


class InstagramLoader{

    fun start() {
            IasApp.instance.run {
                val instagramPackage = getString(R.string.instagram_package)
                packageManager.getLaunchIntentForPackage(instagramPackage)?.let {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
    }

}
