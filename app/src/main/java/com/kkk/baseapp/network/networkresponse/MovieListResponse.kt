package com.kkk.baseapp.network.networkresponse

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kkk.baseapp.data.db.entity.MovieEntity
import com.kkk.baseapp.domain.pojo.MovieDomain
import java.io.Serializable

class MovieListResponse {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

}

open class Movie:Serializable {

    @SerializedName("id")
    @Expose
    var movieId: Int? = null

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("video")
    @Expose
    var video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    @ColumnInfo(name = "is_favorite")
    var favouriteMovie: Int? = 0

    @ColumnInfo(name = "movie_type")
    var movieType: String? = null

}

fun Movie.mapperIntoDomain(): MovieDomain {
    return MovieDomain(0,movieId,adult, backdropPath, genreIds, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title,video, voteAverage, voteCount, favouriteMovie, movieType)
}
fun Movie.mapperIntoEntity(): MovieEntity {
    return MovieEntity(movieId = movieId,adult = adult, backdropPath=backdropPath, genreIds=genreIds, originalLanguage=originalLanguage, originalTitle=originalTitle, overview=overview, popularity=popularity, posterPath=posterPath, releaseDate=releaseDate, title=title,video=video, voteAverage=voteAverage, voteCount=voteCount, favouriteMovie=favouriteMovie, movieType=movieType)
}