package com.key_key.instagramaccessibilityservice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.io.InterruptedIOException


const val TAG = "InstagramAccessService"

class InstagramAccessService : AccessibilityService() {
    private var userName: String = ""
    companion object {
        private const val INSTAGRAM_PACKAGE = "com.instagram.android"
        private const val PROFILE_TAB_ID = "com.instagram.android:id/profile_tab"
        private const val ACTION_BAR_TITLE_ID = "com.instagram.android:id/action_bar_large_title"
    }
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.run {
            source?.let {
                it.refresh()
                fetchUsernameAlgorithm(it)
            }
        }
    }

    private fun fetchUsernameAlgorithm(nodeInfo: AccessibilityNodeInfo) {
        nodeInfo.findAccessibilityNodeInfosByViewId(PROFILE_TAB_ID).apply {
            if (size <= 0) return

            get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)
            userName = nodeInfo.findAccessibilityNodeInfosByViewId(ACTION_BAR_TITLE_ID)[0]
                    .text.trim().toString()
        }

        saveUsername()
        finishFetching()
    }

    private fun finishFetching() {
        disableSelf()
        performBackClick(2)
    }

    private fun saveUsername() {
        Log.d(TAG, userName)
    }


    override fun onInterrupt() {
        throw InterruptedIOException("$TAG interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        setServiceInfo()
        performBackClick(2)
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

    private fun performBackClick(clicks: Int = 1) {
        for (i in 1..clicks) {
            Thread.sleep(100)
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
    }

}
