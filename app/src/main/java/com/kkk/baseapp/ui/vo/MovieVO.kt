package com.kkk.baseapp.ui.vo

import com.kkk.baseapp.domain.pojo.MovieDomain

data class MovieVO(
    var iD: Int = 0,
    var movieId: Int? = null,
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var genreIds: List<Int>? = null,
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var title: String? = null,
    var video: Boolean? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null,
    var favouriteMovie: Int? = 0,
    var movieType: String? = null
)
fun MovieDomain.mapperIntoMovieVO(): MovieVO {
    return MovieVO(iD,movieId,adult, backdropPath, genreIds, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title,video, voteAverage, voteCount, favouriteMovie, movieType)
}