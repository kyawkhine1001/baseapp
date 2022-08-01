package com.kkk.baseapp.data.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.network.networkresponse.*
import com.kkk.baseapp.util.AppConstants
import com.kkk.baseapp.util.MovieType
import com.kkk.mylibrary.utils.SharedUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(
    private val context: Context,
    private val mApiService: ApiService,
    private val database: MyDatabase
) : MainRepository {
    override var popularMovieData: MutableLiveData<Observable<PopularMovieListResponse>> = MutableLiveData()
    override var upcomingMovieData: MutableLiveData<Observable<PopularMovieListResponse>> = MutableLiveData()
    override var trendingMovieData: MutableLiveData<Observable<PopularMovieListResponse>> = MutableLiveData()
    override var favouriteMovieData: MutableLiveData<Observable<PopularMovieListResponse>> = MutableLiveData()
    override var movieDetailData: MutableLiveData<Observable<MovieDetailResponse>> = MutableLiveData()

    override fun fetchPopularMovieData() {
        val localMovieDataList = database.movieDao().allDataByMovieType(MovieType.POPULAR.title)
        val disposable = CompositeDisposable()
        disposable.add(
            localMovieDataList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    disposable.clear()
                    if (SharedUtils.isNetworkAvailable(context) && it.isEmpty()) {
                        popularMovieData.postValue(mApiService.loadMovieList(MovieType.POPULAR.title,AppConstants.apiKey))
                    } else {
                        val responseData = PopularMovieListResponse()
                        responseData.results = it.mappingToPopularList()
                        popularMovieData.postValue(Observable.just(responseData))
                    }
                }
        )
    }
    override fun fetchUpcomingMovieData() {
        val localMovieDataList = database.movieDao().allDataByMovieType(MovieType.UPCOMING.title)
        val disposable = CompositeDisposable()
        disposable.add(
            localMovieDataList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    disposable.clear()
                    if (SharedUtils.isNetworkAvailable(context) && it.isEmpty()) {
                        upcomingMovieData.postValue(mApiService.loadMovieList(MovieType.UPCOMING.title,AppConstants.apiKey))
                    } else {
                        val responseData = PopularMovieListResponse()
                        responseData.results = it
                        upcomingMovieData.postValue(Observable.just(responseData))
                    }
                }
        )
    }

    override fun fetchTrendingMovieData() {
        val localMovieDataList = database.movieDao().allDataByMovieType(MovieType.TRENDING.title)
        val disposable = CompositeDisposable()
        disposable.add(
            localMovieDataList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    disposable.clear()
                    if (SharedUtils.isNetworkAvailable(context) && it.isEmpty()) {
                        trendingMovieData.postValue(mApiService.loadTrendingMovieList(AppConstants.apiKey))
                    } else {
                        val responseData = PopularMovieListResponse()
                        responseData.results = it
                        trendingMovieData.postValue(Observable.just(responseData))
                    }
                }
        )
    }

    override fun fetchFavouriteMovieData() {
        val localMovieDataList = database.movieDao().allFavouriteData
        val disposable = CompositeDisposable()
        disposable.add(
            localMovieDataList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    disposable.clear()
                    val responseData = PopularMovieListResponse()
                    responseData.results = it
                    favouriteMovieData.postValue(Observable.just(responseData))
                }
        )
    }

    override fun savePopularMovieDataIntoDatabase(movieList: List<PopularMovie>) {
        Observable.fromCallable{
            database.movieDao().deleteAllDataByMovieType(MovieType.POPULAR.title)
            movieList.map { it.movieType =  MovieType.POPULAR.title}
            database.movieDao().insertAll(movieList)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun saveUpcomingMovieDataIntoDatabase(movieList: List<UpcomingMovie>) {
        Observable.fromCallable{
            database.movieDao().deleteAllDataByMovieType(MovieType.UPCOMING.title)
            movieList.map { it.movieType =  MovieType.UPCOMING.title}
            database.movieDao().insertAll(movieList)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun saveTrendingMovieDataIntoDatabase(movieList: List<TrendingMovie>) {
        Observable.fromCallable{
            database.movieDao().deleteAllDataByMovieType(MovieType.TRENDING.title)
            movieList.map { it.movieType =  MovieType.TRENDING.title}
            database.movieDao().insertAll(movieList)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun updateFavoriteDataByMovieType(movieId:Int,isFavorite: Int) {
        Observable.fromCallable{ database.movieDao().updateFavoriteDataByMovieType(movieId,isFavorite)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun loadMovieDetail(movieId: Int) {
        movieDetailData.postValue(mApiService.loadMovieDetail(movieId,AppConstants.apiKey))
    }
}