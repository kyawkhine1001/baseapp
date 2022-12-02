package com.kkk.baseapp.ui.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kkk.baseapp.domain.pojo.*

data class MovieDetailVO(
    var id: Int? = null,
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var genres: List<GenreVO>? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var productionCompanies: List<ProductionCompanyVO>? = null,
    var releaseDate: String? = null,
    var runtime: Int? = null,
    var title: String? = null
)

fun MovieDetailDomain.mapperIntoMovieDetailVO(): MovieDetailVO {
    return MovieDetailVO(
        id,
        adult,
        backdropPath,
        genres?.map { it.mapperIntoGenreVO() },
        originalTitle,
        overview,
        productionCompanies?.map { it.mapperIntoProductionCompanyVO() },
        releaseDate,
        runtime,
        title
    )
}

fun GenreDomain.mapperIntoGenreVO(): GenreVO {
    return GenreVO(id, name)
}

fun ProductionCompanyDomain.mapperIntoProductionCompanyVO(): ProductionCompanyVO {
    return ProductionCompanyVO(id, logoPath, name, originCountry)
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

data class GenreVO(
    var id: Int? = null,
    var name: String? = null
)

data class ProductionCompanyVO(
    var id: Int? = null,
    var logoPath: String? = null,
    var name: String? = null,
    var originCountry: String? = null
)

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