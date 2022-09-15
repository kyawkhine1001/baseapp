package com.kkk.baseapp.ui.adapter.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import com.kkk.baseapp.R
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.viewholder.BaseRecyclerViewHolder
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class MovieListViewHolder(itemView: View, val onClick:(PopularMovie) -> Unit) : BaseRecyclerViewHolder<PopularMovie>(itemView) {
    override fun setData(data: PopularMovie) {
        itemView.apply {
            data.posterPath?.let {
                val url = AppConstants.imageBaseUrl+it
//                ivMovie.loadImageWithGlide(itemView.context,url)
            }
//            tvMovieTitle.text = data.title
//            tvMovieRating.text = data.voteAverage?.toString()
//            tvMovieReleaseDate.text = data.releaseDate
//            tvMovieDescription.text = data.overview
//            val favouriteIcon = if ((data.favouriteMovie ?: 0) == 1)  ContextCompat.getDrawable(context,
//                R.drawable.ic_favourite) else ContextCompat.getDrawable(itemView.context,R.drawable.ic_unfavourite)
//            ivFavourite.setImageDrawable(favouriteIcon)
            setOnClickListener { onClick(data) }

        }
    }
}