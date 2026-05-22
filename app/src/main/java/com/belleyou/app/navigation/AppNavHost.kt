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
import com.belleyou.app.features.catalog.presentation.screen.CatalogScreen
import com.belleyou.app.features.favorites.presentation.screen.FavoritesScreen
import com.belleyou.app.features.profile.presentation.screen.ProfileScreen
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

            composable(Routes.Catalog.route) {
                CatalogScreen()
            }

            composable(Routes.Cart.route) {
                CartScreen()
            }

            composable(Routes.Favorites.route) {
                FavoritesScreen()
            }

            composable(Routes.Profile.route) {
                ProfileScreen()
            }
        }
    }
}