package com.kkk.baseapp.ui.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.kkk.baseapp.R
import com.kkk.mylibrary.ui.adapter.viewholder.BaseRecyclerViewHolder

class PageTypeViewHolder(itemView: View,val onClick:(String) -> Unit) : BaseRecyclerViewHolder<String>(itemView) {
    override fun setData(data: String) {
        val tvPageName = itemView.findViewById<TextView>(R.id.tvPageName)
        tvPageName.text = data
        itemView.setOnClickListener { onClick(data) }
    }
}