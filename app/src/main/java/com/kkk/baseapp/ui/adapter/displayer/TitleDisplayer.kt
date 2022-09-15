package com.kkk.baseapp.ui.adapter.displayer

import androidx.databinding.ViewDataBinding
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ItemTitleBinding
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType

class TitleDisplayer(val title:String): ItemDisplayer {
    override fun getViewType(): ViewType = ViewType(R.layout.item_title)
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ItemTitleBinding
        binding.apply {
            tvTitle.text = title
        }
    }

}