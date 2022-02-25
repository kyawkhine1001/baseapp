package com.kkk.baseapp.network

import com.kkk.baseapp.network.networkresponse.MovieDetailResponse
import com.kkk.baseapp.network.networkresponse.PopularMovieListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    @GET("movie/popular")
//    fun loadPopularMovieList(@Query("api_key") apiKey:String): Observable<PopularMovieListResponse>

    @GET("movie/{name}")
    fun loadMovieList(@Path("name") name: String, @Query("api_key") apiKey:String): Observable<PopularMovieListResponse>

    @GET("trending/all/day")
    fun loadTrendingMovieList(@Query("api_key") apiKey:String): Observable<PopularMovieListResponse>

    @GET("movie/{movie_id}")
    fun loadMovieDetail(@Path("movie_id") movie_id: Int, @Query("api_key") apiKey:String): Observable<MovieDetailResponse>
}