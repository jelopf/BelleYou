package com.belleyou.app.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")

    data object Recommendations : Routes("recommendations")

    data object Wishlists : Routes("wishlists")

    data object Cart : Routes("cart")
    //data object Category : Routes("category")
}