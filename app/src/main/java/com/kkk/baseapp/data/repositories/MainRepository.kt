package com.kkk.baseapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.kkk.baseapp.network.networkresponse.*
import io.reactivex.Observable

interface MainRepository {
    var popularMovieData:MutableLiveData<Observable<PopularMovieListResponse>>
    var upcomingMovieData:MutableLiveData<Observable<PopularMovieListResponse>>
    var trendingMovieData:MutableLiveData<Observable<PopularMovieListResponse>>
    var favouriteMovieData:MutableLiveData<Observable<PopularMovieListResponse>>
    var movieDetailData:MutableLiveData<Observable<MovieDetailResponse>>
    fun fetchPopularMovieData()
    fun fetchUpcomingMovieData()
    fun fetchTrendingMovieData()
    fun fetchFavouriteMovieData()
    fun savePopularMovieDataIntoDatabase(movieList:List<PopularMovie>)
    fun saveUpcomingMovieDataIntoDatabase(movieList:List<UpcomingMovie>)
    fun saveTrendingMovieDataIntoDatabase(movieList:List<TrendingMovie>)
    fun updateFavoriteDataByMovieType(movieId:Int,isFavorite: Int)
    fun loadMovieDetail(movieId: Int)
}