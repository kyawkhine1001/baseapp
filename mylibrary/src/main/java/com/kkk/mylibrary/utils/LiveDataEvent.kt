package com.kkk.mylibrary.utils

import androidx.annotation.Nullable

class LiveDataEvent<T>(content: T?) {
    private val mContent: T
    private var hasBeenHandled = false

    @get:Nullable
    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

    init {
        requireNotNull(content) { "null values in Event are not allowed." }
        mContent = content
    }
}