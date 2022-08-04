package com.kkk.mylibrary.ui.other

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBindingBottomSheet<VB:ViewBinding> : BottomSheetDialogFragment() {
    protected lateinit var binding: VB
    abstract val isFullScreen: Boolean
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    abstract fun bindView(inflater: LayoutInflater): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        binding = bindView(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isFullScreen) {
            val dialog: Dialog = dialog as BottomSheetDialog

            dialog.let {
                val bottomSheet: View = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            }

            val view: View? = view
            view?.post {
                val parent: View = view.parent as View
                val params: CoordinatorLayout.LayoutParams =
                    parent.layoutParams as CoordinatorLayout.LayoutParams
                val behavior = params.behavior
                bottomSheetBehavior = behavior as BottomSheetBehavior
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                bottomSheetBehavior.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                    }

                    override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {}
                })
            }
        }
    }

}