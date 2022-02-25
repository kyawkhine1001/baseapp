package com.kkk.baseapp.ui.adapter.displayer

import android.view.View
import com.kkk.baseapp.R
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import kotlinx.android.synthetic.main.item_title.view.*

class TitleDisplayer(val title:String): ItemDisplayer {
    override fun getViewType(): ViewType = ViewType(R.layout.item_title)

    override fun bind(itemView: View) {
        itemView.apply { tvTitle.text = title }
    }

}