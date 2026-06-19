package com.belleyou.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.app.features.product.domain.model.Product

@Composable
fun ProductCard(
    product: Product,  // <-- теперь принимаем реальный объект
    modifier: Modifier = Modifier
) {
    // Локальное состояние для сердечка (пока только UI)
    var isFavorite by remember { mutableStateOf(product.isFavorite) }

    Column(
        modifier = modifier
            .background(colorResource(R.color.white))
    ) {
        // Блок с картинкой (заглушка синим)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(colorResource(R.color.belle_blue))
        ) {
            // Сердечко в правом верхнем углу
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Избранное",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable { isFavorite = !isFavorite },
                tint = if (isFavorite) Color.Red else colorResource(R.color.white)
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Цена и рейтинг
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    product.oldPrice?.let {
                        Text(
                            text = "$it ₽",
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = "${product.price} ₽",
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (product.oldPrice != null) {
                            colorResource(R.color.belle_red)
                        } else {
                            colorResource(R.color.black)
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${product.rating} ★",
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                )
            }

            // Бренд и отзывы
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.brand,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                )
                Text(
                    text = "(${product.reviewsCount})",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = colorResource(R.color.belle_gray)
                )
            }

            // Название товара
            Text(
                text = product.name,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}