package com.kkk.baseapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.databinding.ActivityMovieReviewBinding
import com.kkk.baseapp.ui.adapter.MovieListPagingAdapter
import com.kkk.baseapp.ui.adapter.displayer.MovieListDisplayer
import com.kkk.baseapp.ui.adapter.displayer.TitleDisplayer
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.baseapp.util.UIState
import com.kkk.baseapp.util.enums.MovieType
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class MovieListActivity : BaseViewBindingActivity<ActivityMovieReviewBinding>() {

//    private val mViewModel: MainViewModel by viewModel()

    private val mViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private var mItemList = mutableListOf<ItemDisplayer>()
    private var popularItemList = mutableListOf<ItemDisplayer>()
    private var upcomingItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    private val pagingAdapter: MovieListPagingAdapter = MovieListPagingAdapter()

    companion object{
        private const val IE_NAME = "IE_NAME"
        fun newIntent(context: Context, name:String): Intent {
            val intent = Intent(context,MovieListActivity::class.java)
            intent.putExtra(IE_NAME,name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenStarted {
            loadData()
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMovieReviewBinding
        get() = ActivityMovieReviewBinding::inflate

    override fun setup() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(IE_NAME)
        binding.rvMovieMain.apply {
//            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = mAdapter
//            adapter = pagingAdapter
        }
        loadData()
    }

    override fun listeners() {
        binding.btnRetry.setOnClickListener {
            loadData()
        }
    }

    override fun observers() {
        listenData()
//        listenPopularSuccessData()
//        lifecycleScope.launch {
////            mViewModel.popularMovieListLD.collectLatest(pagingAdapter::submitData)
//            try {
//                mViewModel.popularMovieListLD
//                    .collectLatest {
//                        pagingAdapter.submitData(it)
////                    Log.e("Count",pagingAdapter.loadStateFlow.count().toString())
//                    }
//            }catch (e:java.lang.Exception){
//                Log.e("Error",e.localizedMessage)
//            }
//        }
        lifecycleScope.launch {
            //Your adapter's loadStateFlow here
            pagingAdapter.loadStateFlow.
            distinctUntilChangedBy {
                it.refresh
            }.collect {
                //you get all the data here
//                val list = pagingAdapter.snapshot()
//                    Log.e("Count",list.count().toString())

            }
        }
    }

    private fun listenData() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mViewModel.upcomingMovieListLD
                    .onEach {
                        binding.loadingView.root.visibility = if (it is UIState.Loading) View.VISIBLE else View.GONE
                    }
                    .collectLatest {
                        when (it) {
                            is UIState.Success -> {
                                binding.errorView.visibility = View.GONE
                                mItemList.clear()
//                                upcomingItemList.clear()
                                mItemList.add(TitleDisplayer("Upcoming"))
                                it.successData?.let { movieList ->
                                    mItemList.add(
                                        MovieListDisplayer(
                                            2,
                                            data = movieList,
                                            onClick = ::onClickItem,
                                            onClickFavorite = ::onClickFavourite
                                        )
                                    )
                                }
                                mItemList.add(TitleDisplayer("Popular"))
                                val popularItemDisplayer = MovieListDisplayer(
                                    3,
                                    onClick = ::onClickItem,
                                    onClickFavorite = ::onClickFavourite
                                )
                                mItemList.add(popularItemDisplayer)
//                                mItemList.addAll(upcomingItemList)
//                                mItemList.addAll(popularItemList)
                                mAdapter.setData(mItemList)

                                binding.emptyView.visibility = View.GONE
                                listenPopularSuccessData()
                            }
                            is UIState.Error -> {
                                showErrorMessage(it.message,it.code == 401)
                            }
                            else -> {
                            }
                        }
                    }
            }
        }
    }

    private fun listenPopularSuccessData() {
        lifecycleScope.launch {
            mViewModel.popularMovieListLD
                .onEach {
                    binding.loadingView.root.visibility = if (it is UIState.Loading) View.VISIBLE else View.GONE
                }
                .collectLatest {
                    when (it) {
                        is UIState.Success -> {
                            binding.errorView.visibility = View.GONE
                            val parentDisplayer = mAdapter.getData().getOrNull(3)
                            if (parentDisplayer is MovieListDisplayer) {
                                parentDisplayer.updateList(it.successData)
                            }
                        }
                        is UIState.Error -> {
                            showErrorMessage(it.message,it.code == 401)
                        }
                        else -> {
                            showErrorMessage("Unknown Error")
                        }
                    }
                }
        }
    }

    private fun showErrorMessage(message:String?,isHttpError:Boolean? = false){
        showToast(message)
        binding.errorView.apply {
            visibility = View.VISIBLE
            if (isHttpError == true){
                showNoInternetConnectionView()
            }else{
                showNoRecordFoundView()
            }
        }
    }

    private fun loadData() {
        showLoadingView()
        mViewModel.loadUpcomingMovieList()
        mViewModel.loadPopularMovieList()
    }

    private fun onClickItem(data: MovieVO) {
//        val intent =
//            data.movieId?.let {
//                MovieDetailActivity.newIntent(
//                    this,
//                    data.iD,
//                    it,
//                    data.title!!,
//                    data.favouriteMovie ?: 0,
//                    data.movieType ?: MovieType.POPULAR.title
//                )
//            }
//        startActivity(intent)
    }

    private fun onClickFavourite(parentIndex: Int, childIndex: Int, data: MovieVO) {
        val isFavourite = if (data.favouriteMovie == 0) {
            1
        } else {
            0
        }
        mViewModel.updateFavoriteDataByMovieType(data.iD, isFavourite)
        val parentDisplayer = mAdapter.getData().getOrNull(parentIndex)
        if (parentDisplayer is MovieListDisplayer) {
            data.favouriteMovie = isFavourite
            parentDisplayer.refreshItem(childIndex, data)
        }

    }

}