package com.key_key.instagramaccessibilityservice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent


class InstagramAccessService : AccessibilityService() {

    companion object {
        const val TAG = "InstagramAccessService"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.run {
            Log.e(
                TAG,
                "onAccessibilityEvent: " +
                        "[event: ${getEventType(this)}] " +
//                        "[package: $packageName] " +
                        "[class: $className] " +
                        "[text: ${getEventText(this)}]"
            )
        }
    }

    private fun getEventText(event: AccessibilityEvent): String {
        val sb = StringBuilder()
        for (s in event.text) {
            sb.append(s)
        }
        return sb.toString()
    }

    private fun getEventType(event: AccessibilityEvent) = when (event.eventType) {
        AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> "TYPE_NOTIFICATION_STATE_CHANGED"
        AccessibilityEvent.TYPE_VIEW_CLICKED -> "TYPE_VIEW_CLICKED"
        AccessibilityEvent.TYPE_VIEW_FOCUSED -> "TYPE_VIEW_FOCUSED"
        AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> "TYPE_VIEW_LONG_CLICKED"
        AccessibilityEvent.TYPE_VIEW_SELECTED -> "TYPE_VIEW_SELECTED"
        AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> "TYPE_WINDOW_STATE_CHANGED"
        AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> "TYPE_VIEW_TEXT_CHANGED"
            else -> "default"
    }

    override fun onInterrupt() {
        Log.e(TAG, "onInterrupt")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.v(TAG, "onServiceConnected")
        serviceInfo = AccessibilityServiceInfo().apply {
            flags = AccessibilityServiceInfo.DEFAULT or
                    AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            packageNames = listOf("com.instagram.android").toTypedArray()
        }
    }

}
