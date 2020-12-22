package com.key_key.instagramaccessibilityservice

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity


class MainPresenter (private val view: MainView) : Observer {
    private val app = IasApp.instance
    private val database = app.iasDataBase

    fun onCreate() {
        startAccess()
        view.initButton()
        setText()    // gets data from DB if available
    }

    private fun startAccess() {
        if (!this.checkAccess())  {
            view.openAccessibilitySettings()
        }
    }

    private fun checkAccess(): Boolean {
        with(app) {
            val servicePackageName = app.getString(R.string.service_package_name, packageName)
            val accessServicesList = (getSystemService(AppCompatActivity.ACCESSIBILITY_SERVICE) as AccessibilityManager)
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
            for (service in accessServicesList) {
                if (servicePackageName == service.id) {
                    return true
                }
            }
        }
        return false
    }

    fun onBtnClick() {
        database.addObserver(this)
        startInstagram()
    }

    private fun startInstagram() {
        InstagramLoader().start()
    }

    override fun update() {
        setText()
        database.removeObserver(this)
    }

    private fun setText() {
        if (getNameFromDb().isNullOrBlank()) return
        val text = app.getString(R.string.got_username, getNameFromDb())
        view.setText(text)
    }

    private fun getNameFromDb() =
        database.entityDao?.getUserName()?.userName

}
