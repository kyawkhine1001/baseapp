package com.kkk.mylibrary.ui.activity

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import com.kkk.mylibrary.BaseApp
import com.kkk.mylibrary.R
import com.kkk.mylibrary.utils.extensions.setCurrentLocale
import java.util.*

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    abstract val progressBarStyle:Int
    private var isShowingProgressBar: Boolean = false
    private var progressDialog: Dialog? = null

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            subscribeToLanguageChanges()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addProgressBar(progressBarStyle)
        return inflater.inflate(layoutId,container,false)
    }

    @CallSuper
    open fun onLanguageChanged(newLocale: Locale) {

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun subscribeToLanguageChanges() {
        (requireActivity().application as BaseApp).appLanguage.observe(this, androidx.lifecycle.Observer {
            it?.let {
//                Preferences.setCurrentLocale(this,it.language)
                requireActivity().setCurrentLocale()
                onLanguageChanged(it)
                (requireActivity().application as BaseApp).appLanguage.value = null
            }
        })
    }

    private fun addProgressBar(@StyleRes theme:Int) {
        if (isShowingProgressBar) return
        val dialog = Dialog(requireActivity(), theme)
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
}