package com.kkk.baseapp.ui.adapter.viewholder

import android.view.View
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.viewholder.BaseRecyclerViewHolder

class MovieListViewHolder(itemView: View, val onClick:(MovieVO) -> Unit) : BaseRecyclerViewHolder<MovieVO>(itemView) {
    override fun setData(data: MovieVO) {
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