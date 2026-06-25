package com.belleyou.core.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.buttons.InCartButton
import com.belleyou.core.designsystem.components.layout.SizeSelector
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
                .aspectRatio(1f)
                .background(colorResource(R.color.belle_blue))
        ) {

            if (showFavoriteIcon) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable { onFavoriteClick() },
                    tint = if (isFavorite)
                        Color.Red
                    else
                        colorResource(R.color.white)
                )
            }
        }

        ProductInfoBlock(product)

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