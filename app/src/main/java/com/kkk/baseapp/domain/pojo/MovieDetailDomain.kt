package com.kkk.baseapp.domain.pojo

data class MovieDetailDomain(
    var adult: Boolean? = null,
    var backdropPath: String? = null,
    var genres: List<GenreDomain>? = null,
    var id: Int? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var productionCompanies: List<ProductionCompanyDomain>? = null,
    var releaseDate: String? = null,
    var runtime: Int? = null,
    var title: String? = null,
)

data class BelongsToCollection(
    var id: Int? = null,
    var name: String? = null,
    var posterPath: String? = null,
    var backdropPath: String? = null
)

data class GenreDomain(
    var id: Int? = null,
    var name: String? = null
)

data class ProductionCompanyDomain(
    var id: Int? = null,
    var logoPath: String? = null,
    var name: String? = null,
    var originCountry: String? = null
)

data class ProductionCountry(
    var iso31661: String? = null,
    var name: String? = null
)

data class SpokenLanguage(
    var englishName: String? = null,
    var iso6391: String? = null,
    var name: String? = null
)