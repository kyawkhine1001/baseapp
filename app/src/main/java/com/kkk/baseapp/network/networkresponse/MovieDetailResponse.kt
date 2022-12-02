package com.kkk.baseapp.network.networkresponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kkk.baseapp.domain.pojo.GenreDomain
import com.kkk.baseapp.domain.pojo.MovieDetailDomain
import com.kkk.baseapp.domain.pojo.ProductionCompanyDomain

class MovieDetailResponse {
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    @SerializedName("runtime")
    @Expose
    var runtime: Int? = null

    @SerializedName("title")
    @Expose
    var title: String? = null
}
fun MovieDetailResponse.mapperIntoMovieDetailDomain(): MovieDetailDomain {
    return MovieDetailDomain(
        adult,
        backdropPath,
        genres?.map { it.mapperIntoGenreDomain() },
        id,
        originalTitle,
        overview,
        productionCompanies?.map { it.mapperIntoProductionCompanyDomain() },
        releaseDate,
        runtime,
        title

    )
}

fun Genre.mapperIntoGenreDomain(): GenreDomain {
    return GenreDomain(
        id, name
    )
}

fun ProductionCompany.mapperIntoProductionCompanyDomain(): ProductionCompanyDomain {
    return ProductionCompanyDomain(
        id,
        logoPath,
        name,
        originCountry
    )
}

class BelongsToCollection {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
}

class Genre {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}

class ProductionCompany {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null
}

class ProductionCountry {
    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}

class SpokenLanguage {
    @SerializedName("english_name")
    @Expose
    var englishName: String? = null

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}