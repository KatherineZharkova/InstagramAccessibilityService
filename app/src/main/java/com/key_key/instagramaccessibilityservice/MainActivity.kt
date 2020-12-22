package com.key_key.instagramaccessibilityservice

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstagramButton()
    }

    private fun setUsername(name: String?) {
        findViewById<TextView>(R.id.textView).text = name
    }

    private fun initInstagramButton() {
        findViewById<Button>(R.id.instagram_btn)?.setOnClickListener {
            if (!checkAccess()) offerAccessibilitySettings() else startInstagram()
        }
    }

    private fun checkAccess(): Boolean {
        val packageServiceName = "$packageName/.InstagramAccessService"
        val accessServicesList = (getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager)
            .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
        for (id in accessServicesList) {
            if (packageServiceName == id.id) {
                return true
            }
        }
        return false
    }

    private fun offerAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivityForResult(intent, 0)
    }

    private fun startInstagram() {
        InstagramLoader(this).start()
    }

}
