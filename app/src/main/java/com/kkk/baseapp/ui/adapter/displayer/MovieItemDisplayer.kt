package com.kkk.baseapp.ui.adapter.displayer

import android.view.View
import com.kkk.baseapp.R
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.utils.loadImageWithGlide
import kotlinx.android.synthetic.main.item_movie_item.view.*

class MovieItemDisplayer(val data: PopularMovie, val onClick:(PopularMovie) -> Unit): ItemDisplayer {
    override fun getViewType(): ViewType = ViewType(R.layout.item_movie_item)

    override fun bind(itemView: View) {
        itemView.apply {
            data.posterPath?.let {
                val url = AppConstants.imageBaseUrl+it
                ivMovie.loadImageWithGlide(itemView.context,url)
            }
            tvMovieTitle.text = if(data.title==null) data.title else data.originalTitle
            tvMovieReleaseDate.text = data.releaseDate
            setOnClickListener { onClick(data) }
        }
    }

}