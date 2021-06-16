package com.kkk.mylibrary.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.kkk.mylibrary.ui.adapter.viewholder.BaseRecyclerViewHolder

abstract class BaseRecyclerAdapter<V : BaseRecyclerViewHolder<O>, O> : RecyclerView.Adapter<V>() {
    private var mDataList = mutableListOf<O>()

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.setData(mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun setNewList(newList: MutableList<O>) {
        mDataList = newList
        notifyDataSetChanged()
    }

    fun addData(new: O) {
        mDataList.add(new)
        notifyDataSetChanged()
    }
}
