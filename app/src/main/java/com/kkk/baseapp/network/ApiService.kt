package com.kkk.baseapp.network

import com.kkk.baseapp.network.networkrequest.EMarketPlaceOrderRequest
import com.kkk.baseapp.network.networkresponse.MovieDetailResponse
import com.kkk.baseapp.network.networkresponse.PopularMovieListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

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
    fun getStoreProductList(): Call<List<EMarketShopProductListResponseItem>>

    @POST("order")
    fun makeOrder(@Body request: EMarketPlaceOrderRequest): Call<Void>

}