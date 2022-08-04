package com.kkk.baseapp.ui.adapter.displayer

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ItemCarouselBinding
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType


class CarouselDisplayer(
    val data: List<String>,
    private val onClick: (String) -> Unit,
    val lifecycle: Lifecycle
): ItemDisplayer {
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    override fun getViewType(): ViewType = ViewType(R.layout.item_carousel)
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ItemCarouselBinding
        binding.apply {
            //        itemView.apply {
//            rvMovie.apply {
//                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
//                adapter = mAdapter
//            }
//            data.map {
//                mItemList.add(TitleDisplayer(it))
//            }
//            mAdapter.setData(mItemList)
            // Kotlin
// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
//            carousel.registerLifecycle(lifecycle)
//
//            val list = mutableListOf<CarouselItem>()

// Image URL with caption
//            list.add(
//                CarouselItem(
//                    imageUrl = "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
//                    caption = "Photo by Aaron Wu on Unsplash"
//                )
//            )

// Just image URL
//            list.add(
//                CarouselItem(
//                    imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
//                )
//            )

// Image URL with header
//            val headers = mutableMapOf<String, String>()
//            headers["header_key"] = "header_value"
//
//            list.add(
//                CarouselItem(
//                    imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
//                    headers = headers
//                )
//            )

// Image drawable with caption
//            list.add(
//                CarouselItem(
//                    imageDrawable = R.drawable.ic_launcher_foreground,
//                    caption = "Photo by Kimiya Oveisi on Unsplash"
//                )
//            )

// Just image drawable
//            list.add(
//                CarouselItem(
//                    imageDrawable = R.drawable.ic_launcher_foreground
//                )
//            )
//            carousel.setData(list)
//        }
        }
    }

}