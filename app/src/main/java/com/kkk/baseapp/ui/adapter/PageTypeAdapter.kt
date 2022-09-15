package com.kkk.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kkk.baseapp.R
import com.kkk.baseapp.ui.adapter.viewholder.PageTypeViewHolder
import com.kkk.mylibrary.ui.adapter.BaseRecyclerAdapter

class PageTypeAdapter(private val onClick:(String) -> Unit): BaseRecyclerAdapter<PageTypeViewHolder, String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageTypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_page_type,parent,false)
        return PageTypeViewHolder(view,onClick)
    }
}