package com.belleyou.feature.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.ProductQuantity
import com.belleyou.core.designsystem.components.layout.SizeSelector
import com.belleyou.feature.cart.domain.CartItem

@Composable
fun ProductCardCart(
    modifier: Modifier = Modifier,
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    val product = item.product

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Изображение
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .fillMaxHeight()
                    .background(colorResource(R.color.belle_blue))
                    .clickable { onClick() }
            )

            // Блок с информацией
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick() },
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Название продукта + цена
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {

                    Text(
                        text = product.name,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "${product.price * item.quantity} ₽",
                        fontSize = 12.sp
                    )
                }

                // Прочее
                Column {

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

                    Spacer(modifier = Modifier.height(18.dp))
                }

                // Блоки для управления товарами
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SizeSelector(
                        sizes = item.product.sizes,
                        selectedSize = item.selectedSize,
                        expanded = false,
                        onExpandedChange = {},
                        onSizeSelected = {},
                        enabled = false,
                        modifier = Modifier.width(90.dp)
                    )

                    ProductQuantity(
                        quantity = item.quantity,
                        onIncrease = onIncrease,
                        onDecrease = onDecrease
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart_delete_item),
                        contentDescription = "Удалить",
                        modifier = Modifier.clickable { onDelete() },
                        tint = colorResource(R.color.belle_brown)
                    )
                }
            }
        }

        if (showDivider) {
            HorizontalDivider(
                thickness = 1.2.dp,
                color = colorResource(R.color.belle_under_header)
                    .copy(alpha = 0.3f)
            )
        }
    }
}