package com.belleyou.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.belleyou.app.navigation.Routes
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    val navigationItems = listOf(
        Pair(Icons.Default.Home, Routes.Home.route),
        Pair(Icons.Default.Menu, Routes.Catalog.route),
        Pair(Icons.Default.ShoppingCart, Routes.Cart.route),
        Pair(Icons.Default.Favorite, Routes.Favorites.route),
        Pair(Icons.Default.Person, Routes.Profile.route)
    )

    NavigationBar {

        navigationItems.forEach { item ->

            NavigationBarItem(
                selected =
                    currentRoute == item.second,

                onClick = {

                    navController.navigate(
                        item.second
                    ) {

                        popUpTo(
                            navController.graph.startDestinationId
                        )

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = item.first,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {

    val navController =
        rememberNavController()

    BottomNavigationBar(
        navController = navController
    )
}