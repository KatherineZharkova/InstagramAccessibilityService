package com.example.instagramaccessibilityservice

import android.app.Activity
import android.content.Intent
import android.net.Uri


class InstagramLoader(private val activity: Activity) {

    companion object {
        const val INSTAGRAM_PACKAGE = "com.instagram.android"
    }

    fun load(link: String) {
        activity.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(link))
                .setPackage(INSTAGRAM_PACKAGE)
        )
    }
}
