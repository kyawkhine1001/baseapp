//package com.kkk.baseapp.network.networkresponse
//
//import androidx.annotation.NonNull
//import androidx.room.*
//import com.google.gson.annotations.Expose
//import com.google.gson.annotations.SerializedName
//import com.kkk.baseapp.data.db.typeconverter.IntListTypeConverter
//
//class PopularMovieListResponse {
//    @SerializedName("page")
//    @Expose
//    var page: Int? = null
//
//    @SerializedName("results")
//    @Expose
//    var results: List<Movie>? = null
//
//    @SerializedName("total_pages")
//    @Expose
//    var totalPages: Int? = null
//
//    @SerializedName("total_results")
//    @Expose
//    var totalResults: Int? = null
//
//}
//
////@Entity(tableName = "popular_movie")
//class PopularMovie: Movie()
//
////@Entity(tableName = "upcoming_movie")
//class UpcomingMovie: Movie()
//
////@Entity(tableName = "trending_movie")
//class TrendingMovie: Movie()
//
//@Entity(tableName = "movie")
//@TypeConverters
//open class Movie {
//    @PrimaryKey(autoGenerate = true)
//    var iD: Int = 0
//
//    @SerializedName("id")
//    @Expose
//    var movieId: Int? = null
//
//    @SerializedName("adult")
//    @Expose
//    var adult: Boolean? = null
//
//    @SerializedName("backdrop_path")
//    @Expose
//    var backdropPath: String? = null
//
//    @SerializedName("genre_ids")
//    @Expose
//    var genreIds: List<Int>? = null
//
//    @SerializedName("original_language")
//    @Expose
//    var originalLanguage: String? = null
//
//    @SerializedName("original_title")
//    @Expose
//    var originalTitle: String? = null
//
//    @SerializedName("overview")
//    @Expose
//    var overview: String? = null
//
//    @SerializedName("popularity")
//    @Expose
//    var popularity: Double? = null
//
//    @SerializedName("poster_path")
//    @Expose
//    var posterPath: String? = null
//
//    @SerializedName("release_date")
//    @Expose
//    var releaseDate: String? = null
//
//    @SerializedName("title")
//    @Expose
//    var title: String? = null
//
//    @SerializedName("video")
//    @Expose
//    var video: Boolean? = null
//
//    @SerializedName("vote_average")
//    @Expose
//    var voteAverage: Double? = null
//
//    @SerializedName("vote_count")
//    @Expose
//    var voteCount: Int? = null
//
//    @ColumnInfo(name = "is_favorite")
//    var favouriteMovie: Int? = 0
//
//    @ColumnInfo(name = "movie_type")
//    var movieType: String? = null
//
//
//}
//
//fun List<Movie>.mappingToPopularList():List<PopularMovie>{
//    val movieList:MutableList<PopularMovie> = mutableListOf()
//    this.map {
//        movieList.add(it.mappingToPopular())
//    }
//    return movieList
//}
//fun List<Movie>.mappingToUpcomingList():List<UpcomingMovie>{
//    val movieList:MutableList<UpcomingMovie> = mutableListOf()
//    this.map {
//        movieList.add(it.mappingToUpcoming())
//    }
//    return movieList
//}
//fun List<Movie>.mappingToTrendingList():List<TrendingMovie>{
//    val movieList:MutableList<TrendingMovie> = mutableListOf()
//    this.map {
//        movieList.add(it.mappingToTrending())
//    }
//    return movieList
//}
//fun Movie.mappingToPopular():PopularMovie{
//    val movie = PopularMovie()
//    movie.iD = iD
//    movie.movieId = movieId
//    movie.adult = adult
//    movie.backdropPath = backdropPath
//    movie.genreIds = genreIds
//    movie.originalLanguage = originalLanguage
//    movie.originalTitle = originalTitle
//    movie.overview = overview
//    movie.popularity = popularity
//    movie.posterPath = posterPath
//    movie.releaseDate = releaseDate
//    movie.title = title
//    movie.video = video
//    movie.voteAverage = voteAverage
//    movie.voteCount = voteCount
//    movie.favouriteMovie = favouriteMovie
//    movie.movieType = movieType
//    return movie
//}
//fun Movie.mappingToUpcoming():UpcomingMovie{
//    val movie = UpcomingMovie()
//    movie.iD = iD
//    movie.movieId = movieId
//    movie.adult = adult
//    movie.backdropPath = backdropPath
//    movie.genreIds = genreIds
//    movie.originalLanguage = originalLanguage
//    movie.originalTitle = originalTitle
//    movie.overview = overview
//    movie.popularity = popularity
//    movie.posterPath = posterPath
//    movie.releaseDate = releaseDate
//    movie.title = title
//    movie.video = video
//    movie.voteAverage = voteAverage
//    movie.voteCount = voteCount
//    movie.favouriteMovie = favouriteMovie
//    movie.movieType = movieType
//    return movie
//}
//fun Movie.mappingToTrending():TrendingMovie{
//    val movie = TrendingMovie()
//    movie.iD = iD
//    movie.movieId = movieId
//    movie.adult = adult
//    movie.backdropPath = backdropPath
//    movie.genreIds = genreIds
//    movie.originalLanguage = originalLanguage
//    movie.originalTitle = originalTitle
//    movie.overview = overview
//    movie.popularity = popularity
//    movie.posterPath = posterPath
//    movie.releaseDate = releaseDate
//    movie.title = title
//    movie.video = video
//    movie.voteAverage = voteAverage
//    movie.voteCount = voteCount
//    movie.favouriteMovie = favouriteMovie
//    movie.movieType = movieType
//    return movie
//}