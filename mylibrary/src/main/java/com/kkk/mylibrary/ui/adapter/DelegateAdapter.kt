package com.kkk.mylibrary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.viewholder.DelegateBaseViewHolder

class DelegateAdapter(private val isCustomWidth:Boolean?=false) :
    RecyclerView.Adapter<DelegateBaseViewHolder>() {

    private var items = mutableListOf<ItemDisplayer>()

    override fun getItemViewType(position: Int): Int = items[position].getViewType().layoutId

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DelegateBaseViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), viewType, parent, false
        )
        var viewHolder = DelegateBaseViewHolder(binding)
        isCustomWidth?.let {
            items.map {
                if (it.getViewType().layoutId == viewType) {
                    it.getViewType().layoutWidth?.apply {
                        val layoutParams = binding.root.layoutParams
                        layoutParams.width = this
                        binding.root.layoutParams = layoutParams
                        viewHolder = DelegateBaseViewHolder(binding)
                    }
                }
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DelegateBaseViewHolder, position: Int) =
        items[position].bind(holder.binding)

    fun setData(items: MutableList<ItemDisplayer>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(item: ItemDisplayer) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addDataAtPosition(item: ItemDisplayer,position: Int) {
        this.items.removeAt(position)
        this.items.add(position,item)
        notifyItemChanged(position)
    }

    fun removeLastData() {
        this.items.removeLast()
        notifyDataSetChanged()
    }

    fun getLastData(): ItemDisplayer? {
        return if (this.items.isNotEmpty()) items.last() else null
    }

    fun getCount(): Int {
        return this.items.size
    }

    fun setDataWithPosition(index: Int, items: MutableList<ItemDisplayer>) {
        this.items = items
        notifyItemChanged(index)
    }

    fun setDataWithoutNotify(index: Int, items: MutableList<ItemDisplayer>) {
        this.items = items
    }

    fun notifyItemChanged1(index: Int) {
        notifyItemChanged(index)
    }


    fun addData(items: MutableList<ItemDisplayer>) {
        this.items.addAll(0, items)
        notifyItemRangeInserted(0, items.size)
    }

    fun getData(): MutableList<ItemDisplayer> {
        return this.items
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

}