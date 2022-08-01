package com.kkk.baseapp.ui.adapter.displayer

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ItemMovieItemBinding
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class MovieItemDisplayer(val data: PopularMovie, val onClick:(PopularMovie) -> Unit): ItemDisplayer {
    override fun getViewType(): ViewType = ViewType(R.layout.item_movie_item)
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ItemMovieItemBinding
        binding.apply {
            data.posterPath?.let {
                val url = AppConstants.imageBaseUrl+it
                ivMovie.loadImageWithGlide(root.context,url)
            }
            tvMovieTitle.text = if(data.title==null) data.title else data.originalTitle
            tvMovieReleaseDate.text = data.releaseDate
            val favouriteIcon = if ((data.favouriteMovie ?: 0) == 1)  ContextCompat.getDrawable(root.context,R.drawable.ic_favourite) else ContextCompat.getDrawable(root.context,R.drawable.ic_unfavourite)
            ivFavourite.setImageDrawable(favouriteIcon)
            root.setOnClickListener { onClick(data) }
        }
    }

}