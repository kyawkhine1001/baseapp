package com.kkk.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kkk.baseapp.databinding.ItemMovieItemBinding
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class MovieListPagingAdapter(val onClick:(MovieVO) -> Unit) : PagingDataAdapter<MovieVO, MovieItemViewHolder>(
    MovieItemDiffCallback
    ) {
    private var position: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder =
        MovieItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int){
        getItem(position)?.let { holder.bind(data = it, position,onClick = onClick) }
    }

    companion object{
        private val MovieItemDiffCallback = object  : DiffUtil.ItemCallback<MovieVO>() {
            override fun areItemsTheSame(
                oldItem: MovieVO,
                newItem: MovieVO
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieVO,
                newItem: MovieVO
            ): Boolean {
                return oldItem.movieId == newItem.movieId && oldItem.title == newItem.title
            }
        }
    }
}


class MovieItemViewHolder(
    private val binding: ItemMovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: MovieVO, position: Int, onClick:(MovieVO) -> Unit) {
        data ?: return
        with(binding) {
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
//                onClickFavorite(parentIndex,childIndex,data)
            }
            root.setOnClickListener { onClick(data) }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
        ): MovieItemViewHolder =
            MovieItemViewHolder(
                ItemMovieItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

}