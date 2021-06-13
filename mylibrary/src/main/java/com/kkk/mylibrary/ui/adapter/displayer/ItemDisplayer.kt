package com.kkk.mylibrary.ui.adapter.displayer

import android.view.View

interface ItemDisplayer {
    fun getViewType():ViewType
    fun bind(itemView: View)
}