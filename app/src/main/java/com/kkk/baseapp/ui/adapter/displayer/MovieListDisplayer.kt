package com.kkk.baseapp.ui.adapter.displayer

import androidx.databinding.ViewDataBinding
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ItemListMovieBinding
import com.kkk.baseapp.ui.adapter.MovieListPagingAdapter
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType

class MovieListDisplayer(val index:Int, val data:List<MovieVO>? = null, private val onClick:(MovieVO) -> Unit, private val onClickFavorite:(Int, Int, MovieVO) -> Unit): ItemDisplayer {
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()
    private val pagingAdapter: MovieListPagingAdapter = MovieListPagingAdapter()
//    private val pagingAdapter = DelegatePagingAdapter<MovieVO>()

    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ItemListMovieBinding
        binding.apply {
            rvMovie.apply {
                layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                if (data != null) {
                    adapter = mAdapter
                    data?.mapIndexed {childIndex,item ->
                        mItemList.add(MovieItemDisplayer(index,childIndex,item,onClick,onClickFavorite))
                    }
                    mAdapter.setData(mItemList)
                }else{
                    adapter = pagingAdapter
                }
            }
        }
    }

    override fun getViewType(): ViewType = ViewType(R.layout.item_list_movie)

    fun refreshItem(position:Int,data:MovieVO){
        mAdapter.addDataAtPosition(MovieItemDisplayer(index,position,data,onClick,onClickFavorite),position)
    }

    suspend fun updateList(data:PagingData<MovieVO>){
//            pagingAdapter.submitData(data)
        pagingAdapter.submitData(data)
    }
}
