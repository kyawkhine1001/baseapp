package com.kkk.mylibrary.utils.listeners

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(private val onSingleClick: (v: View?) -> Unit) : View.OnClickListener {
    private val minimumClickInterval: Long = 600
    private var mLastClickTime: Long = 0
    override fun onClick(v: View?) {
        val currentClickTime: Long = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= minimumClickInterval) return
        onSingleClick(v)
    }
}