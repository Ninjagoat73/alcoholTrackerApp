package com.example.alcoholtracker.data.remote

import android.content.Context
import com.google.gson.Gson

class BeerRemoteSource (
    private val context: Context
) {

     fun getBeers() : List<Beer>{

        val beerList = mutableListOf(Beer("Mock", 0.0))


        val jsonString = context.assets.open("beerSearchText.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()

        val result = gson.fromJson(jsonString, BeerSearchResult::class.java)


        for (beerItem in result.beers.items) {
            println("Name: ${beerItem.beer.name}, ABV: ${beerItem.beer.abv}")
            beerList.add(beerItem.beer)
        }

        return beerList
    }
}


