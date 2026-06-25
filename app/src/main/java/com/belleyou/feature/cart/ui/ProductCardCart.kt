package com.belleyou.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.feature.cart.domain.CartItem

@Composable
fun ProductCardCart(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {

    val product = item.product

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier
                .size(66.dp, 100.dp)
                .background(colorResource(R.color.belle_blue))
                .clickable { onClick() }
        )

        Column(
            modifier = Modifier.fillMaxWidth().clickable { onClick() },
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = product.name,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${product.price * item.quantity} ₽",
                    fontSize = 12.sp
                )
            }

            Row {
                Text(
                    text = "арт.",
                    fontSize = 10.sp,
                    color = colorResource(R.color.gray_text)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = product.article,
                    fontSize = 10.sp,
                    color = colorResource(R.color.gray_text)
                )
            }

            Row {
                Text(
                    text = "Цвет:",
                    fontSize = 10.sp,
                    color = colorResource(R.color.gray_text)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "не указан",
                    fontSize = 10.sp,
                    color = colorResource(R.color.gray_text)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier
                        .border(
                            0.3.dp,
                            colorResource(R.color.gray_text)
                        )
                        .height(25.dp)
                        .width(90.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    TextButton(
                        onClick = onDecrease,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("-")
                    }

                    Text(
                        text = item.quantity.toString(),
                        fontSize = 14.sp
                    )

                    TextButton(
                        onClick = onIncrease,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("+")
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_cart_delete_item),
                    contentDescription = "Удалить",
                    modifier = Modifier.clickable {
                        onDelete()
                    },
                    tint = colorResource(R.color.belle_brown)
                )
            }
        }
    }
}