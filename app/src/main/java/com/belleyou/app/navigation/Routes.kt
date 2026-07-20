package com.belleyou.app.navigation

import android.net.Uri

sealed class Routes(val route: String) {

    data object Home : Routes("home")

    data object Recommendations : Routes("recommendations")

    data object Wishlist : Routes("wishlist")

    data object Cart : Routes("cart")

    data object ProductDetail : Routes("product_detail/{id}") {

        const val ARG_ID = "id"

        fun createRoute(id: String): String {
            return "product_detail/$id"
        }
    }

    data object Category : Routes("category/{categoryName}") {

        const val ARG_NAME = "categoryName"

        fun createRoute(categoryName: String): String {
            return "category/${Uri.encode(categoryName)}"
        }
    }
}