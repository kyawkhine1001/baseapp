package com.kkk.mylibrary.utils

import android.util.Patterns

object PhoneNumberUtils {

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        if (!Patterns.PHONE.matcher(phoneNumber)
                .matches() || phoneNumber.length < 7
        ) {
            return false
        }
        return true
    }
}