package com.kkk.mylibrary.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kkk.mylibrary.R
import java.io.Serializable

class BaseSpinnerAdapter<T>(
    private var dataList: List<T>,
    private val isFullView:Boolean? = true,
    private val textColor:Int? = android.R.color.darker_gray,
    private val isBold:Boolean? = false
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        val holder: SimpleSpinnerViewHolder
        val inflater =
            parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_spinner_full_view, parent, false)
            if (isFullView == false){
                view = inflater.inflate(R.layout.list_item_spinner, parent, false)
            }
            holder = SimpleSpinnerViewHolder()
            val tvSpnText = view?.findViewById<View>(R.id.tv_spn_text) as TextView
            isBold?.let {
                if (isBold){
                    tvSpnText.setTypeface(tvSpnText.typeface,Typeface.BOLD)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvSpnText.setTextColor(parent.context.getColor(textColor!!))
            }
            holder.name = tvSpnText
            view.tag = holder
        } else holder = view?.tag as SimpleSpinnerViewHolder

        when {

            dataList[position] is SetUpDataModel -> {
                val data = dataList[position] as SetUpDataModel
                holder.name?.text = data.name
            }


        }

        return view!!

    }

    override fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataList.size
    }

    fun setNewDataList(dataList: List<T>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

}

class SimpleSpinnerViewHolder() {
    var name: TextView? = null
}

open class SetUpDataModel(
    var id: Long? = null,
    var name: String? = null
):Serializable