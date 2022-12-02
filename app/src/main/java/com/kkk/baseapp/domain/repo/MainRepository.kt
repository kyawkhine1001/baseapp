package com.kkk.baseapp.domain.repo

import androidx.paging.PagingData
import com.kkk.baseapp.domain.pojo.MovieDetailDomain
import com.kkk.baseapp.domain.pojo.MovieDomain
import com.kkk.mylibrary.network.ResourceState
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun fetchPopularMovieData(): Flow<PagingData<MovieDomain>>
    suspend fun fetchUpcomingMovieData(): ResourceState<List<MovieDomain>?>
    fun savePopularMovieDataIntoDatabase(movieList:List<MovieDomain>)
    fun saveUpcomingMovieDataIntoDatabase(movieList:List<MovieDomain>)
    fun updateFavoriteDataByMovieType(iD:Int,isFavorite: Int)
    suspend fun fetchMovieDetail(movieId: Int): ResourceState<MovieDetailDomain?>
}