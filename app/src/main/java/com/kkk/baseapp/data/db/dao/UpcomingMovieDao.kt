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
//interface UpcomingMovieDao {
//    @get:Query("select * from upcoming_movie")
//    val allUpcomingData: Observable<List<UpcomingMovie>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllUpcoming(list: List<UpcomingMovie>)
//
//    @Query("delete from upcoming_movie")
//    fun deleteAll()
//}