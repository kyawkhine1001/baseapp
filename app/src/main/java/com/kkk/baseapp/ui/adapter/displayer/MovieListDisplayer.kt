package com.kkk.baseapp.ui.adapter.displayer
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ItemListMovieBinding
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType

class MovieListDisplayer(val data:List<PopularMovie>, private val onClick:(PopularMovie) -> Unit): ItemDisplayer {
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    override fun getViewType(): ViewType = ViewType(R.layout.item_list_movie)
    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ItemListMovieBinding
        binding.apply {
            rvMovie.apply {
                layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = mAdapter
            }
            data.map {
                mItemList.add(MovieItemDisplayer(it,onClick))
            }
            mAdapter.setData(mItemList)
        }
    }

}