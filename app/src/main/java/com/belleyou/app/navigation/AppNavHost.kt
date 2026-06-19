package com.belleyou.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belleyou.app.features.cart.presentation.screen.CartScreen
import com.belleyou.app.features.recommendations.presentation.screen.RecommendationsScreen
import com.belleyou.app.features.wishlists.presentation.screen.WishlistsScreen
import com.belleyou.app.features.home.presentation.screen.HomeScreen
import com.belleyou.app.ui.components.BottomNavigationBar

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
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            composable(Routes.Home.route) {
                HomeScreen()
            }

            composable(Routes.Recommendations.route) {
                RecommendationsScreen()
            }

            composable(Routes.Wishlists.route) {
                WishlistsScreen()
            }

            composable(Routes.Cart.route) {
                CartScreen()
            }
        }
    }
}