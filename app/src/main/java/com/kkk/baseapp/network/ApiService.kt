package com.kkk.baseapp.network

import com.kkk.baseapp.network.networkresponse.MovieDetailResponse
import com.kkk.baseapp.network.networkresponse.PopularMovieListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("storeInfo")
    fun getStoreInfo(): Call<EMarketShopResponse>

    @GET("products")
    fun getStoreProductList(): Call<EMarketShopProductListResponse>

    @POST("order")
    fun makeOrder() : Call<Int>

}