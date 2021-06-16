package com.kkk.mylibrary.mmtext

object MyFontChanger {
    fun changeFont(string: String): String {

        return if (MMFontUtils.isSupportUnicode()) {
            if (MMFontConvert.isZawgyi(string)) {
                MMFontConvert.zawgyiToUni(string).toString()
            } else {
                string
            }
        } else {
            if (MMFontConvert.isZawgyi(string)) {
                string
            } else {
                return MMFontUtils.mmTextUnicodeOrigin(string)
            }

        }
    }
}