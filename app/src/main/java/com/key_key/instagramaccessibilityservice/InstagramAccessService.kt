package com.key_key.instagramaccessibilityservice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.key_key.instagramaccessibilityservice.room.RoomEntity
import java.io.InterruptedIOException


class InstagramAccessService : AccessibilityService() {
    private var userName: String = ""
    companion object {
        private const val TAG = "InstagramAccessService"
        private const val INSTAGRAM_PACKAGE = "com.instagram.android"
        private const val PROFILE_TAB_ID = "com.instagram.android:id/profile_tab"
        private const val ACTION_BAR_TITLE_ID = "com.instagram.android:id/action_bar_large_title"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.source?.run {
            refresh()
            if(userName.isEmpty()) {
                fetchUsernameAlgorithm(this)
            }
        }
    }

    private fun fetchUsernameAlgorithm(nodeInfo: AccessibilityNodeInfo) {
        nodeInfo.findAccessibilityNodeInfosByViewId(PROFILE_TAB_ID).apply {
            try {
                if (get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    userName = nodeInfo.findAccessibilityNodeInfosByViewId(ACTION_BAR_TITLE_ID)[0]
                        .text.trim().toString()
                }
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
                return
            }
        }
        finishFetching()
    }

    private fun finishFetching() {
        saveUsername()
        performDoubleBackClick()
    }

    private fun saveUsername() {
        Log.e("TAG", userName)
        IasApp.instance.iasDataBase.run {
            entityDao?.insert(RoomEntity(userName))
            notifyObservers()
        }
    }

    override fun onInterrupt() {
        throw InterruptedIOException("$TAG interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        setServiceInfo()
        performDoubleBackClick()
    }

    private fun setServiceInfo() {
        serviceInfo = AccessibilityServiceInfo().apply {
            flags = AccessibilityServiceInfo.DEFAULT or
                    AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            packageNames = listOf(INSTAGRAM_PACKAGE).toTypedArray()
        }
    }

    private fun performDoubleBackClick() {
        for (i in 0..1) {
            Thread.sleep(100)
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
    }

}
