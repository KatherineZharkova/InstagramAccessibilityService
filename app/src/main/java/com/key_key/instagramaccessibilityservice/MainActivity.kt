package com.key_key.instagramaccessibilityservice

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.key_key.instagramaccessibilityservice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), MainView {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter.onCreate()
    }

    override fun setText(name: String) {
        binding.textView.text = name
    }

    override fun initButton() {
        binding.instagramBtn.setOnClickListener {
            presenter.onBtnClick()
        }
    }

    override fun openAccessibilitySettings() {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }

}
