package com.belleyou.core.designsystem.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.buttons.InCartButton
import com.belleyou.core.designsystem.components.layout.SizeSelector
import com.belleyou.core.designsystem.components.layout.ShimmerPlaceholder
import com.belleyou.core.model.Product

@Composable
fun ProductCardContent(
    modifier: Modifier = Modifier,
    product: Product,
    isFavorite: Boolean,
    selectedSize: String?,
    sizes: List<String>,
    showFavoriteIcon: Boolean,
    showSizeSelector: Boolean,
    showInCartButton: Boolean,
    showCartAction: Boolean = false,

    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,

    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onSizeSelected: (String) -> Unit,
    onAddToCartClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .background(colorResource(R.color.white))
            .clickable { onClick() }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
                .background(colorResource(R.color.belle_blue))
        ) {
            // Underlay Shimmer
            ShimmerPlaceholder(modifier = Modifier.fillMaxSize())

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .crossfade(500) // 500ms fade
                    .build(),
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (showFavoriteIcon) {
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(32.dp)
                        .clickable { onFavoriteClick() }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = if (isFavorite) colorResource(R.color.belle_blue_dark) else Color.Gray
                        )
                    }
                }
            }
        }

        ProductInfoBlock(
            product = product,
            showCartAction = showCartAction,
            onCartClick = onAddToCartClick
        )

        Spacer(Modifier.height(4.dp))

        if (showSizeSelector) {
            SizeSelector(
                sizes = sizes,
                selectedSize = selectedSize ?: "",
                onExpandedChange = onExpandedChange,
                expanded = expanded,
                onSizeSelected = onSizeSelected,
                modifier = Modifier.padding(start = 6.dp)
            )
        }

        if (showSizeSelector && showInCartButton) {
            Spacer(Modifier.height(12.dp))
        }

        if (showInCartButton) {
            InCartButton(onClick = onAddToCartClick)
        }
    }
}