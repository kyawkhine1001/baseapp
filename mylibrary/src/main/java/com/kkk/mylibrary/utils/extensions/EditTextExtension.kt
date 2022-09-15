package com.kkk.mylibrary.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.kkk.mylibrary.R


fun EditText.afterTextChanged(onChange: (Editable?) -> Unit) {

    val addTextChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            onChange(p0)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
    this.addTextChangedListener(addTextChangedListener)

}

fun EditText.onTextChange(onChange: (CharSequence?) -> Unit) {

    val addTextChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { onChange(p0)}
    }
    this.addTextChangedListener(addTextChangedListener)

}

fun AppCompatEditText.disableEditText() {
    isClickable = false
    isEnabled = false
    isFocusable = false
    setBackgroundResource(R.drawable.bg_edit_profile_edittext_disable)
}