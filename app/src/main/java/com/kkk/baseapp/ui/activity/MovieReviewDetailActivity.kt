package com.kkk.baseapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ActivityMovieReviewDetailBinding
import com.kkk.baseapp.network.networkresponse.MovieDetailResponse
import com.kkk.baseapp.ui.adapter.displayer.MovieListDisplayer
import com.kkk.baseapp.ui.adapter.displayer.TitleDisplayer
import com.kkk.baseapp.ui.vo.MovieDetailVO
import com.kkk.baseapp.util.AppConstants
import com.kkk.baseapp.util.UIState
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.utils.SharedUtils
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class MovieReviewDetailActivity : BaseViewBindingActivity<ActivityMovieReviewDetailBinding>() {

    var movieId: Int? = null
    var isFavouriteMovie: Boolean = false
//    private val mViewModel: MainViewModel by lazy {
//        ViewModelProviders.of(this, MainViewModelFactory(Injection.provideMainRepository(applicationContext), AndroidSchedulerProvider()))
//            .get(MainViewModel::class.java)
//    }

    //    private val mViewModel: MainViewModel by viewModel()
    private val mViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    companion object {
        private const val IE_NAME = "IE_NAME"
        private const val IE_MOVIE_ID = "IE_MOVIE_ID"
        private const val IE_FAVORITE_MOVIE = "IE_FAVORITE_MOVIE"
        fun newIntent(
            context: Context,
            movieId: Int,
            movieName: String,
            favouriteMovie: Int
        ): Intent {
            val intent = Intent(context, MovieReviewDetailActivity::class.java)
            intent.putExtra(IE_NAME, movieName)
            intent.putExtra(IE_MOVIE_ID, movieId)
            intent.putExtra(IE_FAVORITE_MOVIE, favouriteMovie)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(IE_NAME)
        movieId = intent.getIntExtra(IE_MOVIE_ID, 0)
        isFavouriteMovie = intent.getIntExtra(IE_FAVORITE_MOVIE, 0) == 1
        binding.nsMovieDetail.visibility = View.INVISIBLE
        showLoadingView()
        mViewModel.loadMovieDetail(movieId!!)
//        mViewModel.movieDetailSuccessState.observe(this, Observer {
//            bindDetailView(it)
//            binding.nsMovieDetail.visibility = View.VISIBLE
//            binding.emptyViewDetail.visibility = View.GONE
//            hideLoadingView()
//        })
//
//        mViewModel.movieDetailErrorState.observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            binding.emptyViewDetail.visibility = View.VISIBLE
//            hideLoadingView()
//        })
    }

    private fun bindDetailView(movieData: MovieDetailVO) {
        movieData.backdropPath?.let {
            val url = AppConstants.imageBaseUrl + it
            binding.ivMovieDetail.loadImageWithGlide(this, url)
        }
        val runTime: Pair<String, String> = SharedUtils.formatHoursAndMinutes(movieData.runtime!!)
        val runTimeString = "${runTime.first}h ${runTime.second}m"
        binding.tvDetailTitle.text = movieData.originalTitle
        binding.tvDetailReleaseDate.text = "${movieData.releaseDate} | $runTimeString"
        binding.tvDetailOverview.text = movieData.overview
        var genreString = ""
        movieData.genres?.forEachIndexed { index, genre ->
            genreString += genre.name
            genreString += if (index == movieData.genres!!.size - 1) "" else ", "
        }
        binding.tvDetailGenre.text = genreString
        var productionString = ""
        movieData.productionCompanies?.forEachIndexed { index, company ->
            productionString += company.name
            productionString += if (index == movieData.productionCompanies!!.size - 1) "" else ", "
        }
        binding.tvDetailProduction.text = productionString
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMovieReviewDetailBinding
        get() = ActivityMovieReviewDetailBinding::inflate

    override fun setup() {
        // Add menu items without overriding methods in the Activity
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.movie_review_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                val id: Int = menuItem.itemId
                if (id == R.id.menu_favourite) {
                    var isFavourite = 0
                    if (isFavouriteMovie) {
                        isFavourite = 0
                        menuItem.setIcon(android.R.drawable.star_off)
                    } else {
                        isFavourite = 1
                        menuItem.setIcon(android.R.drawable.star_on)
                    }
                    mViewModel.updateFavoriteDataByMovieType(movieId!!, isFavourite)
                    return true
                }
                return true
            }
        })
    }

    override fun observers() {

    }

    override fun listeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mViewModel.movieDetailData
                    .onEach {
                        binding.loadingView.root.visibility =
                            if (it is UIState.Loading) View.VISIBLE else View.GONE
                    }
                    .collectLatest {
                        when (it) {
                            is UIState.Success -> {
                                bindDetailView(it.successData)
                                binding.nsMovieDetail.visibility = View.VISIBLE
                                binding.emptyViewDetail.visibility = View.GONE
                                hideLoadingView()
                            }

                            is UIState.Error -> {
                                Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT)
                                    .show()
                                binding.emptyViewDetail.visibility = View.VISIBLE
                                hideLoadingView()
                            }

                            else -> {
                            }
                        }
                    }
            }
        }
    }
}

//        mViewModel.movieDetailSuccessState.observe(this, Observer {
//            bindDetailView(it)
//            binding.nsMovieDetail.visibility = View.VISIBLE
//            binding.emptyViewDetail.visibility = View.GONE
//            hideLoadingView()
//        })
//
//        mViewModel.movieDetailErrorState.observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            binding.emptyViewDetail.visibility = View.VISIBLE
//            hideLoadingView()
//        })