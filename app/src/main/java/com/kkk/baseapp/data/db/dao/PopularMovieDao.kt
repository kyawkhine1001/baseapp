//package com.kkk.baseapp.data.db.dao
//
//import androidx.room.*
//import com.kkk.baseapp.network.networkresponse.PopularMovie
//import io.reactivex.Observable
//
//@Dao
//interface PopularMovieDao {
//
//    @get:Query("select * from popular_movie")
//    val allPopularData: Observable<List<PopularMovie>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllPopular(list: List<PopularMovie>)
//
//    @Query("delete from popular_movie")
//    fun deleteAll()
//
//}