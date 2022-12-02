package com.kkk.baseapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.kkk.baseapp.data.db.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<MovieEntity>)

    @Query("select * from movie where movie_type=:movieType")
    fun allDataByMovieType(movieType:String): List<MovieEntity>

    @Query("select * from movie where movie_type=:movieType")
    fun allPagingDataByMovieType(movieType:String): PagingSource<Int,MovieEntity>

    @get:Query("select * from movie where is_favorite = 1 group by original_title")
    val allFavouriteData: List<MovieEntity>

    @Query("delete from movie where movie_type=:movieType")
    fun deleteAllDataByMovieType(movieType:String)

//    @Query("update movie set is_favorite=:isFavorite where iD=:iD and movie_type=:movieType")
//    fun updateFavoriteDataByMovieType(iD:Int,isFavorite: Int,movieType:String)

    @Query("update movie set is_favorite=:isFavorite where iD=:iD")
    fun updateFavoriteDataByMovieType(iD:Int,isFavorite: Int)


}