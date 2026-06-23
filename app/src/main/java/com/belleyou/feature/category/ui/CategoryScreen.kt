package com.belleyou.feature.category.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Row
import com.belleyou.core.data.fake.fakeProducts
import com.belleyou.core.designsystem.components.cards.ProductCard

@Composable
fun CategoryScreen(
    categoryName: String,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val filteredProducts = fakeProducts.filter { it.category == categoryName }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeaderBelleYou()
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categoryName,
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Закрыть",
                        modifier = Modifier.size(28.dp),
                        tint = colorResource(id = R.color.black)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val rows = (filteredProducts.size + 1) / 2
                for (row in 0 until rows) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val firstIndex = row * 2
                        val secondIndex = firstIndex + 1

                        if (firstIndex < filteredProducts.size) {
                            ProductCard(
                                showFavoriteIcon = true,
                                product = filteredProducts[firstIndex],
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Box(modifier = Modifier.weight(1f))
                        }

                        if (secondIndex < filteredProducts.size) {
                            ProductCard(
                                showFavoriteIcon = true,
                                product = filteredProducts[secondIndex],
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}