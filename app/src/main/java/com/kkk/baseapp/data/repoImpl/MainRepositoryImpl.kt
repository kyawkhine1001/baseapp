package com.kkk.baseapp.data.repoImpl

import android.content.Context
import androidx.paging.*
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.data.db.entity.mapperIntoDomain
import com.kkk.baseapp.di.hilt.IoDispatcher
import com.kkk.baseapp.domain.pojo.MovieDetailDomain
import com.kkk.baseapp.domain.pojo.MovieDomain
import com.kkk.baseapp.domain.repo.MainRepository
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.network.networkresponse.mapperIntoDomain
import com.kkk.baseapp.network.networkresponse.mapperIntoMovieDetailDomain
import com.kkk.baseapp.util.AppConstants
import com.kkk.baseapp.util.enums.MovieType
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.network.rx.SchedulerProvider
import com.kkk.mylibrary.utils.extensions.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mApiService: ApiService,
    private val database: MyDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : MainRepository {

    @ExperimentalPagingApi
    override fun fetchPopularMovieData(): Flow<PagingData<MovieDomain>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        ),
        remoteMediator = MainRemoteMediator(
            mApiService,
            database
        ),
//        pagingSourceFactory = {database.movieDao().allPagingDataByMovieType(MovieType.POPULAR.title) }
    )
    {
        val dataList = database.movieDao().allDataByMovieType(MovieType.POPULAR.title)
        val data = database.movieDao().allPagingDataByMovieType(MovieType.POPULAR.title)
        data
    }
        .flow.map { it.map { it.mapperIntoDomain() } }
//        return safeApiCall(coroutineDispatcher){mApiService.loadMovieList(MovieType.POPULAR.title,AppConstants.apiKey,1).results?.map { it.mapperIntoDomain() }}
//        return flow<ResourceState<List<MovieDomain>>> {
//            HandleResponseUtil.doNetworkCall(context,mApiService.loadMovieList(MovieType.POPULAR.title,
//                AppConstants.apiKey))
//                .collectLatest {
//                    if (it is ResourceState.Success){
//                        it.successData.results?.let {
//                            send(ResourceState.Success(it.map { it.mapperIntoDomain() }))
//                        }
//                    }else{
//                        send(it.resultToResultWithoutSuccessData(it))
//                    }
//                }
//        }.flowOn(coroutineDispatcher)


    override suspend fun fetchUpcomingMovieData(): ResourceState<List<MovieDomain>?> {
        return safeApiCall(coroutineDispatcher){mApiService.loadMovieList(MovieType.UPCOMING.title,AppConstants.apiKey,1).results?.map { it.mapperIntoDomain() }}

//         try {
//             return mApiService.loadMovieList(MovieType.UPCOMING.title,AppConstants.apiKey).results?.map { it.mapperIntoDomain() }
//        }catch (e:java.lang.Exception){
//
//        }
//        return flow<ResourceState<List<MovieDomain>>> {
//            HandleResponseUtil.doNetworkCall(context,mApiService.loadMovieList(MovieType.UPCOMING.title,AppConstants.apiKey))
//                .collectLatest {
//                    if (it is ResourceState.Success){
//                        it.successData.results?.let {
//                            send(ResourceState.Success(it.map { it.mapperIntoDomain() }))
//                        }
//                    }else{
//                        send(it.resultToResultWithoutSuccessData(it))
//                    }
//                }
//        }.flowOn(coroutineDispatcher)
    }

    override fun savePopularMovieDataIntoDatabase(movieList: List<MovieDomain>) {
//        Observable.fromCallable {
//            database.movieDao().deleteAllDataByMovieType(MovieType.POPULAR.title)
//        }
//            .doOnNext {
//                movieList.map { it.movieType = MovieType.POPULAR.title }
//                database.movieDao().insertAll(movieList.map { it.mapperIntoEntity() })
//            }
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.io())
//            .subscribe()
    }

    override fun saveUpcomingMovieDataIntoDatabase(movieList: List<MovieDomain>) {
//        Observable.fromCallable {
//            database.movieDao().deleteAllDataByMovieType(MovieType.UPCOMING.title)
//        }
//            .doOnNext {
//                movieList.map { it.movieType = MovieType.UPCOMING.title }
//                database.movieDao().insertAll(movieList.map { it.mapperIntoEntity() })
//            }
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.io())
//            .subscribe()
    }

    override fun updateFavoriteDataByMovieType(iD: Int, isFavorite: Int) {
//        Observable.fromCallable {
//            database.movieDao().updateFavoriteDataByMovieType(iD, isFavorite)
//        }
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.io())
//            .subscribe()
    }

    override suspend fun fetchMovieDetail(movieId: Int): ResourceState<MovieDetailDomain?> {
        return safeApiCall(coroutineDispatcher){mApiService.loadMovieDetail(movieId, AppConstants.apiKey).mapperIntoMovieDetailDomain()}

//        return channelFlow<ResourceState<MovieDetailDomain>> {
//            HandleResponseUtil.doNetworkCall(context,mApiService.loadMovieDetail(movieId,AppConstants.apiKey))
//                .collectLatest {
//                    if (it is ResourceState.Success){
//                        send(ResourceState.Success(it.successData.mapperIntoMovieDetailDomain()))
//                    }else{
//                        send(it.resultToResultWithoutSuccessData(it))
//                    }
//                }
//        }.flowOn(coroutineDispatcher)
    }
}