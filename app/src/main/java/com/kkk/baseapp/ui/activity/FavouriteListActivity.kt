package com.kkk.baseapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.databinding.ActivityFavouriteListBinding
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.network.networkresponse.mappingToPopular
import com.kkk.baseapp.ui.adapter.displayer.MovieFavouriteItemDisplayer
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteListActivity : BaseViewBindingActivity<ActivityFavouriteListBinding>() {

//    private val mViewModel: MainViewModel by lazy {
//        ViewModelProviders.of(this, MainViewModelFactory(Injection.provideMainRepository(this),
//            AndroidSchedulerProvider()
//        ))
//            .get(MainViewModel::class.java)
//    }
    private val mViewModel: MainViewModel by viewModel()

    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    companion object{
        private const val IE_NAME = "IE_NAME"
        fun newIntent(context: Context, name:String): Intent {
            val intent = Intent(context,FavouriteListActivity::class.java)
            intent.putExtra(IE_NAME,name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(IE_NAME)
        binding.recyclerViewFavourite.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
        }
        mViewModel.favouriteMovieSuccessState.observe(this, Observer {movieList->
            mItemList.clear()
            movieList.map {
                mItemList.add(MovieFavouriteItemDisplayer(it.mappingToPopular()){ movie->
                    onClickItem(movie)
                })
            }
            mAdapter.setData(mItemList)
            Toast.makeText(this, movieList.size.toString(), Toast.LENGTH_SHORT).show()
            binding.emptyViewFavourite.visibility = if(movieList.isEmpty()) View.VISIBLE else View.GONE
            hideLoadingView()
            Toast.makeText(this, movieList.size.toString(), Toast.LENGTH_SHORT).show()
        })
        mViewModel.favouriteMovieErrorState.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            binding.emptyViewFavourite.visibility = View.VISIBLE
            hideLoadingView()
        })
        showLoadingView()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadFavouriteMovieList()
    }
    private fun onClickItem(data:PopularMovie){
        val intent = MovieReviewDetailActivity.newIntent(this,data.movieId!!,data.title!!,data.favouriteMovie!!)
        startActivity(intent)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityFavouriteListBinding
        get() = ActivityFavouriteListBinding::inflate

    override fun setup() {
    }

    override fun observers() {
    }

    override fun listeners() {
    }

}