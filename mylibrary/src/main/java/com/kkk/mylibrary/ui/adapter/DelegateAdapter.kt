package com.kkk.mylibrary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.ui.adapter.viewholder.BaseViewHolder

class DelegateAdapter : RecyclerView.Adapter<BaseViewHolder>(){

    private var items = mutableListOf<ItemDisplayer>()

    override fun getItemViewType(position: Int): Int = items[position].getViewType().layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = items[position].bind(holder.itemView)

    fun setData(items:MutableList<ItemDisplayer>){
        this.items = items
        notifyDataSetChanged()
    }
}