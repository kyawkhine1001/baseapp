package com.kkk.mylibrary.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kkk.mylibrary.R

abstract class BaseViewBindingFragment<VB : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    private var isShowingProgressBar: Boolean = false
    private var progressDialog: Dialog? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addProgressBar(R.style.ThemeLoadingDialog)
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observers()
        listeners()
    }

    abstract fun setup()
    abstract fun observers()
    abstract fun listeners()


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