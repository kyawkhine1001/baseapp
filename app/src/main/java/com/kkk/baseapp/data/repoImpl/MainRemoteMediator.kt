package com.kkk.baseapp.data.repoImpl

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.data.db.entity.MovieEntity
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.network.networkresponse.mapperIntoEntity
import com.kkk.baseapp.util.AppConstants
import com.kkk.baseapp.util.enums.MovieType
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class MainRemoteMediator(
    private val mApiService: ApiService,
    private val database: MyDatabase
) : RemoteMediator<Int, MovieEntity>() {

    var page = 1
    var hasEndReached = false
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            val nextPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                    if (!hasEndReached) {
                        page++
                        page
                    } else return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val response =
                mApiService.loadMovieList(MovieType.POPULAR.title, AppConstants.apiKey, nextPage)
            Log.e("Response Size : ",response.results?.size.toString())
            hasEndReached = (response.results?.size ?: 0) < 20
//            hasEndReached = page == 2
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieDao().deleteAllDataByMovieType(MovieType.POPULAR.title)
                }
                response.results?.let {
                    val list: List<MovieEntity> = it.map {
                        it.movieType = MovieType.POPULAR.title
                        it.mapperIntoEntity()
                    }
                    database.movieDao().insertAll(list)
                    val dataList = database.movieDao().allDataByMovieType(MovieType.POPULAR.title)
                    Log.e("Inserted list Size : ",dataList.size.toString())
                }
            }
            return MediatorResult.Success(endOfPaginationReached = hasEndReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}