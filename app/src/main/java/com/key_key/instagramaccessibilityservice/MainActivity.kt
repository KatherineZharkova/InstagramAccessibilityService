package com.key_key.instagramaccessibilityservice

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
    }

    override fun setText(name: String) {
        findViewById<TextView>(R.id.textView).text = name
    }

    override fun initButton() {
        findViewById<Button>(R.id.instagram_btn)?.setOnClickListener {
            presenter.onBtnClick()
        }
    }

    override fun openAccessibilitySettings() {
        startActivityForResult(
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS),
            0
        )
    }

}
