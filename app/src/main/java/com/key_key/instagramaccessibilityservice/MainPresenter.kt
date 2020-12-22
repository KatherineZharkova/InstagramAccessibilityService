package com.key_key.instagramaccessibilityservice

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity


class MainPresenter (private val view: MainView) : Observer {
    private val app = IasApp.instance

    private fun getNameFromDb() : String {
        val result = app.iasDataBase.entityDao.getUserName().userName
        Log.e(TAG, "Presenter got $result")
        return result
    }

    private fun setText() {
        view.setText(getNameFromDb())
    }

    fun onBtnClick() {
        app.iasDataBase.addObserver(this)
        if (!checkAccess()) view.offerAccessibilitySettings() else startInstagram()
    }

    private fun checkAccess(): Boolean {
        with(app) {
            val packageServiceName = "$packageName/.InstagramAccessService"
            val accessServicesList = (getSystemService(AppCompatActivity.ACCESSIBILITY_SERVICE) as AccessibilityManager)
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
            for (id in accessServicesList) {
                if (packageServiceName == id.id) {
                    return true
                }
            }
        }
        return false
    }

    private fun startInstagram() {
        InstagramLoader().start()
    }

    override fun update() {
        setText()
        app.iasDataBase.removeObserver(this)
    }

}
