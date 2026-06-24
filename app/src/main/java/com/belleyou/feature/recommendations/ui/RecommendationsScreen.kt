package com.belleyou.feature.recommendations.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.buttons.SquareButton
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecommendationsScreen(
    viewModel: RecommendationsViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderBelleYou()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = stringResource(R.string.recommendations_screen_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.belle_black)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }

                    uiState.products.isEmpty() -> {
                        Text("Нет доступных товаров")
                    }

                    else -> {
                        val product = uiState.products.first()

                        ProductCard(
                            uiModel = product,
                            isFavorite = false,
                            showFavoriteIcon = false,
                            onClick = { /* open detail */ },
                            onFavoriteClick = { /* toggle favorite */ },
                            onAddToCartClick = { /* add to cart */ }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                SquareButton(
                    resId = R.drawable.ic_skip,
                    contentDescription = "Пропустить",
                    mainColor = colorResource(R.color.belle_red),
                    modifier = Modifier.size(40.dp),
                    onClick = { /* next product */ }
                )

                SquareButton(
                    resId = R.drawable.ic_wishlists,
                    contentDescription = "В вишлист",
                    mainColor = colorResource(R.color.belle_blue_dark),
                    modifier = Modifier.size(40.dp),
                    onClick = { /* toggle favorite */ }
                )

                SquareButton(
                    resId = R.drawable.ic_cart,
                    contentDescription = "В корзину",
                    mainColor = colorResource(R.color.belle_brown),
                    modifier = Modifier.size(40.dp),
                    onClick = { /* add to cart */ }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecommendationsScreenPreview() {
    RecommendationsScreen()
}
