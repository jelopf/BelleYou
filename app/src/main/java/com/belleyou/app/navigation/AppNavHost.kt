package com.belleyou.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.belleyou.core.designsystem.components.navigation.BottomNavigationBar
import com.belleyou.feature.cart.ui.CartScreen
import com.belleyou.feature.category.ui.CategoryScreen
import com.belleyou.feature.home.ui.HomeScreen
import com.belleyou.feature.product.ui.ProductDetailScreen
import com.belleyou.feature.recommendations.ui.RecommendationsScreen
import com.belleyou.feature.wishlist.ui.WishlistScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            composable(Routes.Home.route) {
                HomeScreen(
                    onCategoryClick = { categoryName ->
                    navController.navigate("category/$categoryName")
                },
                    onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                })
            }

            composable(Routes.Recommendations.route) {
                RecommendationsScreen()
            }

            composable(Routes.Wishlists.route) {
                WishlistScreen()
            }

            composable(Routes.Cart.route) {
                CartScreen()
            }

            composable(
                route = Routes.ProductDetail(0).route,
                arguments = listOf(
                    navArgument(Routes.ProductDetail.ARG_ID) { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt(Routes.ProductDetail.ARG_ID) ?: 0
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = "category/{categoryName}",
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Категория"
                CategoryScreen(
                    categoryName = categoryName,
                    navController = navController
                )
            }
        }
    }
}