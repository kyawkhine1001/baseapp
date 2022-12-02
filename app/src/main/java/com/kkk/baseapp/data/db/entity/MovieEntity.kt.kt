package com.kkk.baseapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kkk.baseapp.domain.pojo.MovieDomain

@Entity(tableName = "movie")
@TypeConverters
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var iD: Int = 0,

    var movieId: Int? = null,

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name = "genre_ids")
    var genreIds: List<Int>? = null,

    @ColumnInfo(name = "original_language")
    var originalLanguage: String? = null,

    @ColumnInfo(name = "original_title")
    var originalTitle: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "popularity")
    var popularity: Double? = null,

    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "release_date")
    var releaseDate: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "video")
    var video: Boolean? = null,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double? = null,

    @ColumnInfo(name = "vote_count")
    var voteCount: Int? = null,

    @ColumnInfo(name = "is_favorite")
    var favouriteMovie: Int? = 0,

    @ColumnInfo(name = "movie_type")
    var movieType: String? = null

)
fun MovieEntity.mapperIntoDomain(): MovieDomain {
    return MovieDomain(iD,movieId,adult, backdropPath, genreIds, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title,video, voteAverage, voteCount, favouriteMovie, movieType)
}
fun MovieDomain.mapperIntoEntity(): MovieEntity {
    return MovieEntity(iD,movieId,adult, backdropPath, genreIds, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title,video, voteAverage, voteCount, favouriteMovie, movieType)
}