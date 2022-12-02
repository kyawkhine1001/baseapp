package com.kkk.baseapp.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kkk.baseapp.domain.usecase.*
import com.kkk.baseapp.ui.vo.MovieDetailVO
import com.kkk.baseapp.ui.vo.MovieVO
import com.kkk.baseapp.ui.vo.mapperIntoMovieDetailVO
import com.kkk.baseapp.ui.vo.mapperIntoMovieVO
import com.kkk.baseapp.util.UIState
import com.kkk.baseapp.util.toUIStateWithoutSuccess
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUpcomingMovieListUseCase: GetUpcomingMovieListUseCase,
    private val getPopularMovieListUseCase: GetPopularMovieListUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val addFavouriteMovieUseCase: AddFavouriteMovieUseCase
) : BaseViewModel() {

    private var _upcomingMovieListLD: MutableStateFlow<UIState<List<MovieVO>>> = MutableStateFlow(
        UIState.Loading
    )
    val upcomingMovieListLD: StateFlow<UIState<List<MovieVO>>> = _upcomingMovieListLD

    private var _popularMovieListLD: MutableStateFlow<UIState<PagingData<MovieVO>>> = MutableStateFlow(
        UIState.Loading
    )
    val popularMovieListLD: StateFlow<UIState<PagingData<MovieVO>>> = _popularMovieListLD

//    private var _popularMovieListLD: MutableStateFlow<PagingData<MovieVO>> = MutableStateFlow(PagingData.empty())
//    var popularMovieListLD: Flow<PagingData<MovieVO>> = _popularMovieListLD

    private var _movieDetailDataLD: MutableStateFlow<UIState<MovieDetailVO>> =
        MutableStateFlow(UIState.Loading)
    val movieDetailData: StateFlow<UIState<MovieDetailVO>> get() = _movieDetailDataLD


    init {
        viewModelScope.launch {
            _popularMovieListLD.value = UIState.Loading
            try {
                getPopularMovieListUseCase()
                    .distinctUntilChanged()
                    .map {
                        it.map {
                            it.mapperIntoMovieVO()
                        }
                    }
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _popularMovieListLD.value = UIState.Success(it)
                    }
            }catch (e:Exception){
                _popularMovieListLD.value = UIState.Error(400,e.localizedMessage)
                Log.e("Error",e.localizedMessage)
            }
        }
    }
    fun loadUpcomingMovieList() {
        _upcomingMovieListLD.value = UIState.Loading
        viewModelScope.launch {
            getUpcomingMovieListUseCase()
                .let {
                    if (it is ResourceState.Success){
                        it.successData?.let {
                            _upcomingMovieListLD.value  = UIState.Success(it.map { it.mapperIntoMovieVO() })
                        }
                    }else{
                        _upcomingMovieListLD.value = it.toUIStateWithoutSuccess()
                    }
                }
        }
    }

    fun loadPopularMovieList() {
//        popularMovieListLD = getPopularMovieListUseCase()
////            .distinctUntilChanged()
//            .map { it.map { it.mapperIntoMovieVO() } }
//            .cachedIn(viewModelScope)
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
//                initialValue = PagingData.empty()
//            )
//            .map {
////                        val movieList = mutableListOf<MovieVO>()
////                        it.map { movieList.add(it.mapperIntoMovieVO()) }
//                _popularMovieListLD.value = UIState.Success(it.map { it.mapperIntoMovieVO() })
//            }
//        _popularMovieListLD.value = UIState.Loading
//        viewModelScope.launch {
//            try {
//                getPopularMovieListUseCase()
//                    .distinctUntilChanged()
//                    .cachedIn(viewModelScope)
//                    .map {
////                        val movieList = mutableListOf<MovieVO>()
////                        it.map { movieList.add(it.mapperIntoMovieVO()) }
//                        _popularMovieListLD.value = UIState.Success(it.map { it.mapperIntoMovieVO() })
//                    }
//            }catch (e:Exception){
//                Log.e("Error",e.localizedMessage)
//            }
////                .let {
////                    if (it is ResourceState.Success){
////                        it.successData?.let {
////                            _popularMovieListLD.value = UIState.Success(it.map { it.mapperIntoMovieVO() })
////                        }
////                    }else{
////                        _popularMovieListLD.value = it.toUIStateWithoutSuccess()
////                    }
////                }
//        }
    }

//    var upcomingMovieErrorState = MutableLiveData<String>()
//    var upcomingMovieSuccessState = MutableLiveData<List<Movie>>()
//
//    var popularMovieErrorState = MutableLiveData<String>()
//    var popularMovieSuccessState = MutableLiveData<List<Movie>>()
//
//    var movieDetailErrorState = MutableLiveData<String>()
//    var movieDetailSuccessState = MutableLiveData<MovieDetailResponse>()

//    fun loadUpcomingMovieList() {
//        mainRepo.upcomingMovieData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(schedulers.io())
//                        .observeOn(schedulers.mainThread())
//                        .doOnNext {
//                            it.results?.let { it1 -> mainRepo.saveUpcomingMovieDataIntoDatabase(it1) }
//                        }
//                        .subscribe({ response ->
//                            mainRepo.upcomingMovieData = MutableLiveData()
//                            upcomingMovieSuccessState.postValue(response.results)
//                        }, { error ->
//                            upcomingMovieErrorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//        mainRepo.fetchUpcomingMovieData()
//    }
//
//    fun loadPopularMovieList() {
//        mainRepo.popularMovieData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(schedulers.io())
//                        .observeOn(schedulers.mainThread())
//                        .doOnNext {
//                            it.results?.let { it1 -> mainRepo.savePopularMovieDataIntoDatabase(it1) }
//                        }
//                        .subscribe({ response ->
//                            mainRepo.popularMovieData = MutableLiveData()
//                            popularMovieSuccessState.postValue(response.results)
//                        }, { error ->
//                            popularMovieErrorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//
//
//        mainRepo.fetchPopularMovieData()
//    }

    fun updateFavoriteDataByMovieType(id:Int,isFavorite: Int) {
        addFavouriteMovieUseCase(id,isFavorite)
    }

    fun loadMovieDetail(movieId:Int){
        _movieDetailDataLD.value = UIState.Loading
        viewModelScope.launch {
            getMovieDetailUseCase(movieId)
                .let {
                    if (it is ResourceState.Success){
                        it.successData?.let {
                            _movieDetailDataLD.value = (UIState.Success(it.mapperIntoMovieDetailVO()))
                        }
                    }else{
                        _movieDetailDataLD.value = it.toUIStateWithoutSuccess()
                    }
                }
        }
//        mainRepo.movieDetailData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(schedulers.io())
//                        .observeOn(schedulers.mainThread())
//                        .subscribe({ response ->
//                            mainRepo.movieDetailData = MutableLiveData()
//                            movieDetailSuccessState.postValue(response)
//                        }, { error ->
//                            movieDetailErrorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//        mainRepo.fetchMovieDetail(movieId)
    }
}
