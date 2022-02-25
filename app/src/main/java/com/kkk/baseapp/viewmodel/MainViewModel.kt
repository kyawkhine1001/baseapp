package com.kkk.baseapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kkk.androidarchitectures.viewmodels.BaseViewModel
import com.kkk.baseapp.data.repositories.MainRepository
import com.kkk.baseapp.network.networkresponse.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val mainRepo: MainRepository
) : BaseViewModel() {
    var popularMovieErrorState = MutableLiveData<String>()
    var popularMovieSuccessState = MutableLiveData<List<PopularMovie>>()

    var upcomingMovieErrorState = MutableLiveData<String>()
    var upcomingMovieSuccessState = MutableLiveData<List<PopularMovie>>()

    var trendingMovieErrorState = MutableLiveData<String>()
    var trendingMovieSuccessState = MutableLiveData<List<PopularMovie>>()

    var favouriteMovieErrorState = MutableLiveData<String>()
    var favouriteMovieSuccessState = MutableLiveData<List<Movie>>()

    var movieDetailErrorState = MutableLiveData<String>()
    var movieDetailSuccessState = MutableLiveData<MovieDetailResponse>()


    fun loadPopularMovieList() {
        mainRepo.popularMovieData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            it.results?.let { it1 -> mainRepo.savePopularMovieDataIntoDatabase(it1.mappingToPopularList()) }
                        }
                        .subscribe({ response ->
                            mainRepo.popularMovieData = MutableLiveData()
                            popularMovieSuccessState.postValue(response.results?.mappingToPopularList())
                        }, { error ->
                            popularMovieErrorState.postValue(error.localizedMessage)
                        })
                }
            }


        mainRepo.fetchPopularMovieData()
    }

    fun loadUpcomingMovieList() {
        mainRepo.upcomingMovieData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            it.results?.let { it1 -> mainRepo.saveUpcomingMovieDataIntoDatabase(it1.mappingToUpcomingList()) }
                        }
                        .subscribe({ response ->
                            mainRepo.trendingMovieData = MutableLiveData()
                            upcomingMovieSuccessState.postValue(response.results?.mappingToPopularList())
                        }, { error ->
                            trendingMovieErrorState.postValue(error.localizedMessage)
                        })
                }
            }
        mainRepo.fetchUpcomingMovieData()
    }

    fun loadTrendingMovieList() {
        mainRepo.trendingMovieData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            it.results?.let { it1 -> mainRepo.saveTrendingMovieDataIntoDatabase(it1.mappingToTrendingList()) }
                        }
                        .subscribe({ response ->
                            mainRepo.upcomingMovieData = MutableLiveData()
                            trendingMovieSuccessState.postValue(response.results?.mappingToPopularList())
                        }, { error ->
                            trendingMovieErrorState.postValue(error.localizedMessage)
                        })
                }
            }
        mainRepo.fetchTrendingMovieData()
    }

    fun loadFavouriteMovieList() {
        mainRepo.favouriteMovieData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            mainRepo.favouriteMovieData = MutableLiveData()
                            favouriteMovieSuccessState.postValue(response.results)
                        }, { error ->
                            favouriteMovieErrorState.postValue(error.localizedMessage)
                        })
                }
            }
        mainRepo.fetchFavouriteMovieData()
    }

    fun updateFavoriteDataByMovieType(movieId:Int,isFavorite: Int) {
        mainRepo.updateFavoriteDataByMovieType(movieId,isFavorite)
    }

    fun loadMovieDetail(movieId:Int){
        mainRepo.movieDetailData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            mainRepo.movieDetailData = MutableLiveData()
                            movieDetailSuccessState.postValue(response)
                        }, { error ->
                            movieDetailErrorState.postValue(error.localizedMessage)
                        })
                }
            }
        mainRepo.loadMovieDetail(movieId)
    }
}
