package com.kkk.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kkk.baseapp.R
import com.kkk.baseapp.ui.adapter.viewholder.MovieListViewHolder
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.mylibrary.ui.adapter.BaseRecyclerAdapter

class MovieListAdapter(private val onClick:(MovieVO) -> Unit): BaseRecyclerAdapter<MovieListViewHolder, MovieVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie,parent,false)
        return MovieListViewHolder(view,onClick)
    }
}