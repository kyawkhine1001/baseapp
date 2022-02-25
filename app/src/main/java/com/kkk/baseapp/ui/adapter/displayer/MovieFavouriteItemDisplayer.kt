package com.kkk.baseapp.ui.adapter.displayer

import android.view.View
import com.kkk.baseapp.R
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.utils.loadImageWithGlide
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieFavouriteItemDisplayer(val data: PopularMovie, val onClick:(PopularMovie) -> Unit): ItemDisplayer {
    override fun getViewType(): ViewType = ViewType(R.layout.list_item_movie)

    override fun bind(itemView: View) {
        itemView.apply {
            data.posterPath?.let {
                val url = AppConstants.imageBaseUrl+it
                itemView.ivMovie.loadImageWithGlide(itemView.context,url)
            }
            tvMovieTitle.text = data.title
            tvMovieRating.text = data.voteAverage?.toString()
            tvMovieReleaseDate.text = data.releaseDate
            tvMovieDescription.text = data.overview
            setOnClickListener { onClick(data) }
        }
    }

}