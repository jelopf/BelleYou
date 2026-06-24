package com.belleyou.feature.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.belleyou.app.R
import com.belleyou.feature.cart.domain.model.ProductCardCart
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderBelleYou()

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.3f)
                .height(0.6.dp),
            color = colorResource(R.color.belle_brown)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.cart_screen_title),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "(${uiState.cartItems.size})",
                color = colorResource(id = R.color.gray_text),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (uiState.cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Корзина пуста",
                        fontSize = 14.sp,
                        color = colorResource(R.color.gray_text)
                    )
                }

            } else {
                uiState.cartItems.forEach { item ->

                    ProductCardCart(
                        item = item,
                        onIncrease = {
                            viewModel.increaseQuantity(item.product.id)
                        },
                        onDecrease = {
                            viewModel.decreaseQuantity(item.product.id)
                        },
                        onDelete = {
                            viewModel.removeFromCart(item.product.id)
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "ИТОГО",
                    fontSize = 12.sp
                )

                Text(
                    text = "${uiState.totalPrice} ₽",
                    fontSize = 12.sp
                )
            }

            Button(
                onClick = { /* checkout */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.belle_blue),
                    contentColor = colorResource(R.color.belle_black)
                ),
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "ВЫБРАТЬ МАГАЗИН",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen()
}