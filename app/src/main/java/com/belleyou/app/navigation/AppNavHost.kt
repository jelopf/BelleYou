package com.belleyou.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.belleyou.core.designsystem.components.navigation.BottomNavigationBar
import com.belleyou.feature.cart.ui.CartScreen
import com.belleyou.feature.category.ui.CategoryScreen
import com.belleyou.feature.home.ui.HomeScreen
import com.belleyou.feature.product.ui.ProductDetailScreen
import com.belleyou.feature.recommendations.ui.RecommendationsScreen
import com.belleyou.feature.wishlist.ui.WishlistScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
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
                        navController.navigate(
                            Routes.Category.createRoute(categoryName)
                        )
                    },
                    onProductClick = { productId ->
                        navController.navigate(
                            Routes.ProductDetail.createRoute(productId)
                        )
                    }
                )
            }

            composable(Routes.Recommendations.route) {
                RecommendationsScreen()
            }

            composable(Routes.Wishlist.route) {
                WishlistScreen()
            }

            composable(Routes.Cart.route) {
                CartScreen()
            }

            composable(
                route = Routes.ProductDetail.route,
                arguments = listOf(
                    navArgument(Routes.ProductDetail.ARG_ID) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val productId =
                    backStackEntry.arguments
                        ?.getInt(Routes.ProductDetail.ARG_ID)
                        ?: 0

                ProductDetailScreen(
                    productId = productId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Routes.Category.route,
                arguments = listOf(
                    navArgument(Routes.Category.ARG_NAME) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val categoryName =
                    backStackEntry.arguments
                        ?.getString(Routes.Category.ARG_NAME)
                        ?: "Категория"

                CategoryScreen(
                    categoryName = categoryName,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onProductClick = { productId ->
                        navController.navigate(
                            Routes.ProductDetail.createRoute(productId)
                        )
                    }
                )
            }
        }
    }
}