package com.kkk.mylibrary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.kkk.mylibrary.ui.adapter.viewholder.BaseRecyclerViewHolder

abstract class BaseRecyclerAdapter<V : BaseRecyclerViewHolder<O>, in O>(context: Context) : RecyclerView.Adapter<V>() {
    private var mDataList: List<O>
    protected var mInflater: LayoutInflater

    init {
        mDataList = ArrayList()
        mInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.setData(mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun setNewList(newList: List<O>) {
        mDataList = newList
        notifyDataSetChanged()
    }
}
