package com.kkk.baseapp.ui.fragment.moviereview.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.databinding.FragmentUpcomingMovieBinding
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.ui.activity.MovieReviewDetailActivity
import com.kkk.baseapp.ui.adapter.MovieListAdapter
import com.kkk.baseapp.ui.adapter.displayer.MovieListDisplayer
import com.kkk.baseapp.ui.adapter.displayer.TitleDisplayer
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.ui.fragment.BaseViewBindingFragment
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingMovieFragment : BaseViewBindingFragment<FragmentUpcomingMovieBinding>() {
    private val mMovieListAdapter: MovieListAdapter by lazy { MovieListAdapter{
        onClickItem(it)
    } }

    private fun onClickItem(data:PopularMovie){
        val intent =
            data.movieId?.let {
                MovieReviewDetailActivity.newIntent(requireContext(),
                    it,data.title!!,data.favouriteMovie!!)
            }
        startActivity(intent)
    }

//    private val mViewModel: MainViewModel by lazy {
//        ViewModelProviders.of(this, MainViewModelFactory(Injection.provideMainRepository(requireContext()), AndroidSchedulerProvider()))
//            .get(MainViewModel::class.java)
//    }
    private val mViewModel: MainViewModel by viewModel()

    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter:DelegateAdapter= DelegateAdapter()

    override fun onResume() {
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewUpcomingMovie.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext, 1)
            adapter = mAdapter
        }
        listenData()
        loadData()
        binding.ivRetry.setOnClickListener {
            loadData()
        }

    }
    private fun listenData(){
        mViewModel.upcomingMovieSuccessState.observe(viewLifecycleOwner, Observer {
            mItemList.clear()
            mItemList.add(TitleDisplayer("Upcoming"))
            mItemList.add(MovieListDisplayer(it){movie->
                onClickItem(movie)
            })
            mAdapter.setData(mItemList)
            Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_SHORT).show()
            binding.emptyView.visibility = View.GONE
            hideLoadingView()

            mViewModel.trendingMovieSuccessState.observe(viewLifecycleOwner, Observer {
                mItemList.add(TitleDisplayer("Trending"))
                mItemList.add(MovieListDisplayer(it){movie->
                    onClickItem(movie)
                })
                mAdapter.setData(mItemList)
                Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_SHORT).show()
                binding.emptyView.visibility = View.GONE
                hideLoadingView()

                mViewModel.popularMovieSuccessState.observe(viewLifecycleOwner, Observer {
                    mItemList.add(TitleDisplayer("Popular"))
                    mItemList.add(MovieListDisplayer(it){movie->
                        onClickItem(movie)
                    })
                    mAdapter.setData(mItemList)
                })
            })
        })

        mViewModel.upcomingMovieErrorState.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            binding.emptyView.visibility = if(mItemList.isEmpty()) View.VISIBLE else View.GONE
            hideLoadingView()
        })
        mViewModel.trendingMovieErrorState.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            binding.emptyView.visibility = if(mItemList.isEmpty()) View.VISIBLE else View.GONE
            hideLoadingView()
        })
    }
    private fun loadData(){
        showLoadingView()
        mViewModel.loadUpcomingMovieList()
        mViewModel.loadTrendingMovieList()
        mViewModel.loadPopularMovieList()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUpcomingMovieBinding
        get() = FragmentUpcomingMovieBinding::inflate

    override fun setup() {
    }

    override fun observers() {
    }

    override fun listeners() {
    }

}