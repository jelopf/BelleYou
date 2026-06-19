package com.belleyou.app.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
            .aspectRatio(0.8f)
            .background(colorResource(id = R.color.white))
    ) {
        // Блок с картинкой (заглушка синим)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(colorResource(id = R.color.belle_blue))
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
                tint = if (isFavorite) Color.Red else colorResource(id = R.color.white)
            )
        }

        // Цена и рейтинг
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${product.price} ₽",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${product.rating} ★",
                fontSize = 14.sp,
                color = colorResource(id = R.color.black)
            )
        }

        // Название товара
        Text(
            text = product.name,
            fontSize = 14.sp,
            color = colorResource(id = R.color.black),
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 0.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}