package com.kkk.baseapp.network

import com.kkk.baseapp.network.networkrequest.EMarketPlaceOrderRequest
import com.kkk.baseapp.network.networkresponse.MovieDetailResponse
import com.kkk.baseapp.network.networkresponse.MovieListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @GET("movie/popular")
//    fun loadPopularMovieList(@Query("api_key") apiKey:String): Observable<PopularMovieListResponse>

    @GET("movie/{name}")
    suspend fun loadMovieList(@Path("name") name: String, @Query("api_key") apiKey:String,@Query("page") page: Int): MovieListResponse

//    @GET("trending/all/day")
//    fun loadTrendingMovieList(@Query("api_key") apiKey:String): Observable<PopularMovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun loadMovieDetail(@Path("movie_id") movie_id: Int, @Query("api_key") apiKey:String): MovieDetailResponse

    @GET("storeInfo")
    fun getStoreInfo(): Call<EMarketShopResponse>

    @GET("products")
    fun getStoreProductList(): Call<List<EMarketShopProductListResponseItem>>

    @POST("order")
    fun makeOrder(@Body request: EMarketPlaceOrderRequest): Call<Void>

}