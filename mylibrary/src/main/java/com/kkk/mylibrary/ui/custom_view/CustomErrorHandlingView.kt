package com.kkk.mylibrary.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.kkk.mylibrary.R
import com.kkk.mylibrary.utils.extensions.showImage

class CustomErrorHandlingView : ConstraintLayout {

    private lateinit var ivErrorPhoto: ImageView
    private lateinit var tvErrorMessage: TextView
    private lateinit var btnTryAgain: Button
    var onClickTryAgain : (() -> Unit?)? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val view = View.inflate(context, R.layout.layout_error_handling, this)
        ivErrorPhoto = view.findViewById(R.id.iv_error_photo)
        tvErrorMessage = view.findViewById(R.id.tv_error_message)
        btnTryAgain = view.findViewById(R.id.btn_try_again)

    }

    fun showNoInternetConnectionView() {
        context.showImage(
            ivErrorPhoto,
            imageUrl = "",
            errorHandlerView = ContextCompat.getDrawable(context,R.drawable.ic_no_internet_connection)
        )
        tvErrorMessage.text = "No internet connection"
        btnTryAgain.visibility = View.VISIBLE
        btnTryAgain.setOnClickListener {
            onClickTryAgain?.let { it1 -> it1() }
        }
    }

    fun showNoRecordFoundView() {
        context.showImage(
            ivErrorPhoto,
            imageUrl = "",
            errorHandlerView = ContextCompat.getDrawable(context,R.drawable.ic_no_record_found)
        )
        tvErrorMessage.text = "No record found"
        btnTryAgain.visibility = View.GONE
    }

    fun showServerErrorView() {
        context.showImage(
            ivErrorPhoto,
            imageUrl = "",
            errorHandlerView = ContextCompat.getDrawable(context,R.drawable.ic_server_errror)
        )
        tvErrorMessage.text = "Server error"
        btnTryAgain.visibility = View.VISIBLE
        btnTryAgain.setOnClickListener {
            onClickTryAgain?.let { it1 -> it1() }
        }
    }

    fun showOtherErrorView(image: Int, errorMessage: String, showTryAgain: Boolean?= false, tryAgainLabel:String? = context.getString(R.string.txt_try_again)) {

        context.showImage(
            ivErrorPhoto,
            imageUrl = "",
            errorHandlerView = ContextCompat.getDrawable(context,image)
        )
        tvErrorMessage.text = errorMessage
        if (showTryAgain == true) {
            btnTryAgain.text = tryAgainLabel
            btnTryAgain.visibility = View.VISIBLE
            btnTryAgain.setOnClickListener {
                onClickTryAgain?.let { it1 -> it1() }
            }
        } else {
            btnTryAgain.visibility = View.GONE
        }

    }


}