package com.kkk.baseapp.ui.adapter.displayer

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import kotlinx.android.synthetic.main.item_list_movie.view.*


class MovieListDisplayer(val data:List<PopularMovie>, private val onClick:(PopularMovie) -> Unit): ItemDisplayer {
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    override fun getViewType(): ViewType = ViewType(R.layout.item_list_movie)

    override fun bind(itemView: View) {
        itemView.apply {
            rvMovie.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = mAdapter
            }
            data.map {
                mItemList.add(MovieItemDisplayer(it,onClick))
            }
            mAdapter.setData(mItemList)
        }
    }

}