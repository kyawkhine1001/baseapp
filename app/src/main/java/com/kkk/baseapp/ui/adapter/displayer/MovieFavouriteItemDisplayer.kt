package com.kkk.baseapp.ui.adapter.displayer

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ListItemMovieBinding
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class MovieFavouriteItemDisplayer(val data: MovieVO, val onClick:(MovieVO) -> Unit): ItemDisplayer {
    override fun getViewType(): ViewType = ViewType(R.layout.list_item_movie)
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ListItemMovieBinding
        binding.apply {
            data.posterPath?.let {
                val url = AppConstants.imageBaseUrl+it
                ivMovie.loadImageWithGlide(root.context,url)
            }
            tvMovieTitle.text = data.title
            tvMovieRating.text = data.voteAverage?.toString()
            tvMovieReleaseDate.text = data.releaseDate
            tvMovieDescription.text = data.overview
            val favouriteIcon = if ((data.favouriteMovie ?: 0) == 1)  ContextCompat.getDrawable(root.context,R.drawable.ic_favourite) else ContextCompat.getDrawable(root.context,R.drawable.ic_unfavourite)
            ivFavourite.setImageDrawable(favouriteIcon)
            root.setOnClickListener { onClick(data) }
        }
    }

}