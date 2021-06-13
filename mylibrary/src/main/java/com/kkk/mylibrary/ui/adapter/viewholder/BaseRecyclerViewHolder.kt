package com.kkk.mylibrary.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder<in O>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setData(data: O)
}