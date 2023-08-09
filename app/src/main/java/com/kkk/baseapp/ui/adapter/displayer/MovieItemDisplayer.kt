package com.kkk.baseapp.ui.adapter.displayer

import androidx.databinding.ViewDataBinding
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ItemMovieItemBinding
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class MovieItemDisplayer(private val parentIndex:Int, private val childIndex:Int, val data: MovieVO, val onClick:(MovieVO) -> Unit, private val onClickFavorite:(Int, Int, MovieVO) -> Unit): ItemDisplayer {
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ItemMovieItemBinding
        binding.apply {
            data.posterPath?.let {
                val url = AppConstants.imageBaseUrl+it
                ivMovie.loadImageWithGlide(root.context,url)
            }
            tvMovieTitle.text = if(data.title==null) data.title else data.originalTitle
            tvMovieReleaseDate.text = data.releaseDate
            if (data.favouriteMovie == 0){
                ivFavourite.setImageResource(android.R.drawable.star_off)
            }else{
                ivFavourite.setImageResource(android.R.drawable.star_on)
            }
            ivFavourite.setOnClickListener {
//                if (data.favouriteMovie == 0){
//                    ivFavourite.setImageResource(android.R.drawable.star_on)
//                }else{
//                    ivFavourite.setImageResource(android.R.drawable.star_off)
//                }
                onClickFavorite(parentIndex,childIndex,data)
            }
            root.setOnClickListener {
                onClick(data)
            }
        }
    }

    override fun getViewType(): ViewType = ViewType(R.layout.item_movie_item)


}