//package com.kkk.baseapp.data.db.dao
//
//import androidx.room.*
//import com.kkk.baseapp.network.networkresponse.Movie
//import com.kkk.baseapp.network.networkresponse.PopularMovie
//import com.kkk.baseapp.network.networkresponse.TrendingMovie
//import com.kkk.baseapp.network.networkresponse.UpcomingMovie
//import io.reactivex.Observable
//
//@Dao
//interface TrendingMovieDao {
//
//    @get:Query("select * from trending_movie")
//    val allTrendingData: Observable<List<TrendingMovie>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllTrending(list: List<TrendingMovie>)
//
//    @Query("delete from trending_movie")
//    fun deleteAll(list:List<TrendingMovie>)
//}