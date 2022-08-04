package com.kkk.mylibrary.ui.adapter.displayer

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.kkk.mylibrary.R
import com.kkk.mylibrary.databinding.ListItemCustomOptionsBinding
import com.kkk.mylibrary.vo.OptionType

class CustomBottomSheetDisplayer<T>(
    private val data: OptionType<T>,
    private val position: Int,
    private val onClick: (Int) -> Unit
) :
    ItemDisplayer {
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ListItemCustomOptionsBinding

        binding.tvMoreOption.text = binding.root.context.resources.getString(data.text)
        data.image?.let {
            Glide.with(binding.root.context)
                .load(ContextCompat.getDrawable(binding.root.context, it))
                .into(binding.ivMoreOption)
        }

        binding.root.setOnClickListener {
            onClick(position)
        }

    }

    override fun getViewType(): ViewType = ViewType(R.layout.list_item_custom_options)
}