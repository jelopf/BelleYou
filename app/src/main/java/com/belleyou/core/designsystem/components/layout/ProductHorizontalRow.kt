package com.belleyou.core.designsystem.components.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.model.Product

@Composable
fun ProductHorizontalRow(
    title: String,
    products: List<Product>,
    favorites: Set<String>,
    onProductClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title.uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
            TextButton(onClick = onSeeAllClick) {
                Text(
                    text = "СМОТРЕТЬ ВСЕ",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        
        LazyRow(
            state = state,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    isFavorite = favorites.contains(product.id),
                    showFavoriteIcon = true,
                    modifier = Modifier.width(160.dp),
                    onClick = { onProductClick(product.id) },
                    onFavoriteClick = { onFavoriteClick(product.id) }
                )
            }
        }
    }
}