package com.kkk.baseapp.ui.adapter.viewholder

import android.view.View
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.viewholder.BaseRecyclerViewHolder
import com.kkk.mylibrary.utils.loadImageWithGlide
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListViewHolder(itemView: View, val onClick:(PopularMovie) -> Unit) : BaseRecyclerViewHolder<PopularMovie>(itemView) {
    override fun setData(data: PopularMovie) {
        data.posterPath?.let {
            val url = AppConstants.imageBaseUrl+it
            itemView.ivMovie.loadImageWithGlide(itemView.context,url)
        }
        itemView.tvMovieTitle.text = data.title
        itemView.tvMovieRating.text = data.voteAverage?.toString()
        itemView.tvMovieReleaseDate.text = data.releaseDate
        itemView.tvMovieDescription.text = data.overview
        itemView.setOnClickListener { onClick(data) }
    }
}