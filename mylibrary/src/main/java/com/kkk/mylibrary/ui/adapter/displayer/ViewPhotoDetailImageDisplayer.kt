package com.kkk.mylibrary.ui.adapter.displayer

import android.view.View
import androidx.databinding.ViewDataBinding
import com.kkk.mylibrary.R
import com.kkk.mylibrary.databinding.ListItemViewPhotoDetailImageBinding
import com.kkk.mylibrary.utils.ImageUtil
import com.kkk.mylibrary.utils.extensions.showImage

class ViewPhotoDetailImageDisplayer(private val position:Int, private val image : String, private val isBase64: Boolean? = false, private val onClickImage: ((Int)-> Unit)?=null) :
    ItemDisplayer
{

    override fun getViewType(): ViewType  = ViewType(R.layout.list_item_view_photo_detail_image)

    override fun bind(vb: ViewDataBinding) {
        val viewBinding = vb as ListItemViewPhotoDetailImageBinding

        viewBinding.apply {
            if (isBase64 == true){
                ivProductDetailImage.setImageBitmap(ImageUtil.bitmapFromBase64(image))
            }else{
                root.context.showImage(ivProductDetailImage,image)
            }
            root.setOnClickListener { onClickImage?.let { it1 -> it1(position) } }
        }


    }

}