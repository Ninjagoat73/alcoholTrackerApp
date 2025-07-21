package com.example.alcoholtracker.data.remote

import com.google.gson.annotations.SerializedName

data class BeerSearchResult(
    val beers: BeersSection
)

data class BeersSection(
    val items: List<BeerItem>
)

data class BeerItem(
    val beer: Beer
)

data class Beer(
    @SerializedName("beer_name") val name: String,
    @SerializedName("beer_abv") val abv: Double
)
