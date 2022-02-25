package com.kkk.baseapp.data.db.dao

import androidx.room.*
import com.kkk.baseapp.network.networkresponse.Movie
import com.kkk.baseapp.network.networkresponse.PopularMovie
import com.kkk.baseapp.network.networkresponse.TrendingMovie
import com.kkk.baseapp.network.networkresponse.UpcomingMovie
import com.kkk.baseapp.util.MovieType
import io.reactivex.Observable

@Dao
interface MovieDao {

//    @get:Query("select * from movie")
//    val allPopularData: Observable<List<PopularMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Movie>)

    @Query("select distinct * from movie where movie_type=:movieType")
    fun allDataByMovieType(movieType:String): Observable<List<Movie>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllUpcoming(list: List<UpcomingMovie>)
//
//    @get:Query("select * from movie")
//    val allTrendingData: Observable<List<TrendingMovie>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllTrending(list: List<TrendingMovie>)

    @get:Query("select * from movie where is_favorite = 1 group by originalTitle")
    val allFavouriteData: Observable<List<Movie>>

    @Query("delete from movie where movie_type=:movieType")
    fun deleteAllDataByMovieType(movieType:String)

    @Query("update movie set is_favorite=:isFavorite where movieId=:movieId")
    fun updateFavoriteDataByMovieType(movieId:Int,isFavorite: Int)


//    @get:Query("select * from popular_movie where is_favorite = 0 union all select * from upcoming_movie where is_favorite = 0 union all select * from trending_movie where is_favorite = 0")
//    val allFavouriteData: Observable<List<Movie>>

}