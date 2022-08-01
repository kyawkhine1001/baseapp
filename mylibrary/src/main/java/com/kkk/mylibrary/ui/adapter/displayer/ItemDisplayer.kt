package com.kkk.mylibrary.ui.adapter.displayer

import androidx.databinding.ViewDataBinding

interface ItemDisplayer {
    fun getViewType():ViewType
    fun bind(vb: ViewDataBinding)
}