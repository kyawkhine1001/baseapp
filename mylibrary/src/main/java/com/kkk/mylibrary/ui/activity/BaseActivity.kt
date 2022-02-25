package com.kkk.mylibrary.ui.activity

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import com.kkk.mylibrary.BaseApp
import com.kkk.mylibrary.R
import com.kkk.mylibrary.utils.setCurrentLocale
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val progressBarStyle:Int
    private var isShowingProgressBar: Boolean = false
    private var progressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setCurrentLocale()
            subscribeToLanguageChanges()
        }
        addProgressBar(progressBarStyle)
    }

    @CallSuper
    open fun onLanguageChanged(newLocale: Locale) {

    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun subscribeToLanguageChanges() {
        (application as BaseApp).appLanguage.observe(this, androidx.lifecycle.Observer {
            it?.let {
//                Preferences.setCurrentLocale(this,it.language)
                setCurrentLocale()
                onLanguageChanged(it)
                (application as BaseApp).appLanguage.value = null
            }
        })
    }

    private fun addProgressBar(@StyleRes theme:Int) {
        if (isShowingProgressBar) return
        val dialog = Dialog(this, theme)
        dialog.setContentView(R.layout.loading)
        dialog.setCancelable(false)
        progressDialog = dialog
    }

    fun showLoadingView() {
        if (isShowingProgressBar) return
        progressDialog?.show()
        isShowingProgressBar = true
    }

    fun hideLoadingView() {
        progressDialog?.hide()
        isShowingProgressBar = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}