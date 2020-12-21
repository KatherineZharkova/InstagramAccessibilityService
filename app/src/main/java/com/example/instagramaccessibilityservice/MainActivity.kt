package com.example.instagramaccessibilityservice

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstagramButton()
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
