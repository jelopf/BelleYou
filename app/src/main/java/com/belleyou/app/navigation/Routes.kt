package com.belleyou.app.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")

    data object Recommendations : Routes("recommendations")

    data object Wishlists : Routes("wishlists")

    data object Cart : Routes("cart")

    data class ProductDetail(val id: Int) : Routes("product_detail/{id}") {
        companion object {
            const val ARG_ID = "id"
        }
    }
    //data object Category : Routes("category")
}