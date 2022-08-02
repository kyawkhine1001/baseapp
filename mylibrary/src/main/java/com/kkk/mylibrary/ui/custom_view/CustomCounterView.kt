package com.kkk.mylibrary.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.kkk.mylibrary.R

class CustomCounterView : LinearLayout {

    private lateinit var rlMinus:RelativeLayout
    private lateinit var rlPlus:RelativeLayout
    private lateinit var tvCount:TextView
    private var buttonListener: CounterButtonListener?=null
    private var maxCount:Int? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val view = View.inflate(context, R.layout.view_custom_counter_view, this)
        rlMinus = view.findViewById<RelativeLayout>(R.id.rlMinus)
        rlPlus = view.findViewById<RelativeLayout>(R.id.rlPlus)
        tvCount = view.findViewById<TextView>(R.id.tvCount)
        setBackgroundForPlusMinusButton(tvCount.text.toString().toInt())
        rlMinus.setOnClickListener {
            val count = tvCount.text.toString().toInt()-1
            if (count>0){
                tvCount.text = count.toString()
            }
            setBackgroundForPlusMinusButton(count)
            this.buttonListener?.onClickMinusButton()
        }
        rlPlus.setOnClickListener {
            if (tvCount.text.toString().toInt() < maxCount ?: 1){
                val count = tvCount.text.toString().toInt()+1
                tvCount.text = count.toString()
                setBackgroundForPlusMinusButton(count)
                this.buttonListener?.onClickPlusButton()
            }
        }
    }

    private fun setBackgroundForPlusMinusButton(count:Int){
        if (count>1 || count == maxCount){
            rlMinus.setBackgroundResource(R.drawable.bg_counter_left_selected)
        }else{
            rlMinus.setBackgroundResource(R.drawable.bg_counter_left_default)
        }
        if (count<1 || count == maxCount){
            rlPlus.setBackgroundResource(R.drawable.bg_counter_right_default)
        }else{
            rlPlus.setBackgroundResource(R.drawable.bg_counter_right_selected)
        }
    }

    fun setCurrentCount(count:Int) {
        tvCount.text = count.toString()
        setBackgroundForPlusMinusButton(count)
    }

    fun getCurrentCount():Int = tvCount.text.toString().toInt()

    fun setOnClickListeners(listener: CounterButtonListener){
        this.buttonListener = listener
    }

    fun setMaxCount(count:Int){
        this.maxCount = count
        maxCount?.let {
            val currentCount = tvCount.text.toString().toInt()
            if (currentCount == it){
                rlPlus.setBackgroundResource(R.drawable.bg_counter_right_default)
            }else{
                rlPlus.setBackgroundResource(R.drawable.bg_counter_right_selected)
            }
        }
    }

    interface CounterButtonListener{
        fun onClickMinusButton()
        fun onClickPlusButton()
    }
}