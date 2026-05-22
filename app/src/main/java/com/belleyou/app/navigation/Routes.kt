package com.belleyou.app.navigation

sealed class Routes(val route: String) {

    data object Home : Routes("home")

    data object Catalog : Routes("catalog")

    data object Cart : Routes("cart")

    data object Favorites : Routes("favorites")

    data object Profile : Routes("profile")
}