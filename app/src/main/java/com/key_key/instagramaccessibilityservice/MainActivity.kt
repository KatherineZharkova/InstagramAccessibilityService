package com.key_key.instagramaccessibilityservice

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "InstagramAccessService"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstagramButton()
        if (!checkAccess()) {
            offerAccessibilitySettings()
        }

    }

    private fun checkAccess(): Boolean {
        val packageServiceName = "$packageName/.InstagramAccessService"
        val accessServicesList = (getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager)
            .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
        for (id in accessServicesList) {
            if (packageServiceName == id.id) {
                Log.e(TAG, "InstagramAccessService has access")
                return true
            }
        }
        Log.e(TAG, "InstagramAccessService needs access")
        return false
    }

    private fun offerAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivityForResult(intent, 0)
    }

    private fun initInstagramButton() {
        findViewById<Button>(R.id.instagram_btn)?.setOnClickListener {
            startInstagram()
        }
    }

    private fun startInstagram() {
        InstagramLoader(this).load(getString(R.string.app_link))
    }



}
