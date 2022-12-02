package com.kkk.baseapp.domain.pojo

open class MovieDomain(
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