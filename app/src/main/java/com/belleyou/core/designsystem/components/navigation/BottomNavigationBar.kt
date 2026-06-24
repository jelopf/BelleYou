package com.belleyou.core.designsystem.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.belleyou.app.navigation.Routes
import com.belleyou.app.R

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val navigationItems = listOf(
        Pair(R.drawable.ic_home, Routes.Home.route),
        Pair(R.drawable.ic_recommendations, Routes.Recommendations.route),
        Pair(R.drawable.ic_wishlists, Routes.Wishlist.route),
        Pair(R.drawable.ic_cart, Routes.Cart.route)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .navigationBarsPadding()
    ) {
        HorizontalDivider(
            thickness = 0.6.dp,
            color = colorResource(id = R.color.belle_gray)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationItems.forEach { item ->
                val isSelected = currentRoute == item.second

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (!isSelected) {
                                navController.navigate(item.second) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = if (isSelected) colorResource(id = R.color.belle_blue) else Color.Unspecified,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.first),
                            contentDescription = null,
                            modifier = Modifier.size(19.dp),
                            tint = colorResource(id = R.color.belle_black)
                        )
                    }
                }
            }
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