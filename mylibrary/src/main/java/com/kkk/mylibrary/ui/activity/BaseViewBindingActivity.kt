package com.kkk.mylibrary.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kkk.mylibrary.BaseApp
import com.kkk.mylibrary.R
import java.util.*

abstract class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _hasConnection: Boolean = false
    protected val hasConnection : Boolean
    get() = _hasConnection

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    private var isShowingProgressBar: Boolean = false
    private var progressDialog: Dialog? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setup()
        observers()
        listeners()
//        checkConnection()
        subscribeToLanguageChanges()
    }

    abstract fun setup()
    abstract fun observers()
    abstract fun listeners()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // click on 'up' button in the action bar, handle it here
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    open fun onLanguageChanged(newLocale: Locale) {

    }

    private fun subscribeToLanguageChanges() {
        (application as BaseApp).appLanguage.observe(this) {
            it?.let {
                onLanguageChanged(it)
            }
        }
    }

    private fun addProgressBar(@StyleRes theme:Int) {
        if (isShowingProgressBar && progressDialog?.isShowing == true) return
        val dialog = Dialog(this, theme)
        dialog.setContentView(R.layout.loading)
        dialog.setCancelable(false)
        progressDialog = dialog
    }

    fun showLoadingView() {
        if (isShowingProgressBar && progressDialog?.isShowing == true) return
        progressDialog?.show()
        isShowingProgressBar = true
    }

    fun hideLoadingView() {
        progressDialog?.hide()
        isShowingProgressBar = false
    }
}