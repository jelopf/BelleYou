package com.belleyou.feature.category.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.data.mock.fakeProducts
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import com.belleyou.core.model.toUiModel

@Composable
fun CategoryScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    val filteredProducts = remember(categoryName) {
        fakeProducts.filter { it.category == categoryName }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
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
                color = colorResource(R.color.black),
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Закрыть",
                    modifier = Modifier.size(28.dp),
                    tint = colorResource(R.color.black)
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

            repeat(rows) { row ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    val firstIndex = row * 2
                    val secondIndex = firstIndex + 1

                    // Левая карточка
                    if (firstIndex < filteredProducts.size) {

                        val product = filteredProducts[firstIndex]

                        ProductCard(
                            uiModel = product.toUiModel(),
                            isFavorite = false,
                            showFavoriteIcon = true,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onProductClick(product.id)
                            }
                        )

                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    // Правая карточка
                    if (secondIndex < filteredProducts.size) {

                        val product = filteredProducts[secondIndex]

                        ProductCard(
                            uiModel = product.toUiModel(),
                            isFavorite = false,
                            showFavoriteIcon = true,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onProductClick(product.id)
                            }
                        )

                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}