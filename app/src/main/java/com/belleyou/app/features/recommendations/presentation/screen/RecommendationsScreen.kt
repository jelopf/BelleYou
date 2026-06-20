package com.belleyou.app.features.recommendations.presentation.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.app.core.data.fake.product.fakeProducts
import com.belleyou.app.ui.components.HeaderBelleYou
import com.belleyou.app.ui.components.ProductCard

@Composable
fun RecommendationsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderBelleYou()
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.recommendations_screen_title),
                        color = colorResource(id = R.color.belle_black),
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 22.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (fakeProducts.isNotEmpty()) {
                        ProductCard(
                            product = fakeProducts[0],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp)
                        )
                    } else {
                        Text("Нет доступных товаров")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SquareButton(
                        resId = R.drawable.ic_skip,
                        contentDescription = "Пропустить",
                        mainColor = colorResource(id = R.color.belle_red),
                        modifier = Modifier.size(40.dp),
                        onClick = { /* TODO */ }
                    )

                    SquareButton(
                        resId = R.drawable.ic_wishlists,
                        contentDescription = "В вишлист",
                        mainColor = colorResource(id = R.color.belle_blue_dark),
                        modifier = Modifier.size(width = 41.dp, height = 35.dp),
                        onClick = { /* TODO */ }
                    )

                    SquareButton(
                        resId = R.drawable.ic_cart,
                        contentDescription = "В корзину",
                        mainColor = colorResource(id = R.color.belle_brown),
                        modifier = Modifier.size(width = 36.dp, height = 34.dp),
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecommendationsScreenPreview() {
    RecommendationsScreen()
}
