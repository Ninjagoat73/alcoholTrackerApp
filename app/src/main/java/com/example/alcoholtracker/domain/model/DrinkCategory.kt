package com.example.alcoholtracker.domain.model

enum class DrinkCategory(id: Int, name: String, defaultABV: Double) {
    BEER(1,"Beer", 6.0),
    WINE(2,"Wine",12.0),
    SPIRIT(3, "Spirit",39.0),
    COCKTAIL(4,"Cocktail", 10.0),
    CIDER(5, "Cider", 6.0),
    LIQUEUR(6,"Liqueur", 21.0),
    MIXED_SHOT(7,"Mixed Shot", 19.5),
    OTHER(8,"Other", 0.0)
}