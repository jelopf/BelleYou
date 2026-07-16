package com.belleyou.feature.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.ProductQuantity
import com.belleyou.core.model.Product
import com.belleyou.feature.cart.domain.CartItem

@Composable
fun ProductCardCart(
    modifier: Modifier = Modifier,
    item: CartItem,
    isSelected: Boolean,
    isFavorite: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit,
    onFavoriteToggle: () -> Unit,
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
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Selection Checkbox
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChange,
                modifier = Modifier.padding(top = 0.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(R.color.belle_black),
                    uncheckedColor = Color.LightGray
                )
            )

            // Product Image
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp, 140.dp)
                    .background(colorResource(R.color.belle_blue))
                    .clickable { onClick() },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Info Block
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name.uppercase(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Light,
                            lineHeight = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "арт. ${product.article}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Цвет: ${product.category}", // Mock color as we don't have it explicitly
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onFavoriteToggle() },
                        tint = if (isFavorite) colorResource(R.color.belle_blue_dark) else Color.LightGray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Size and Quantity Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Size Selector (Simple box for now)
                    Box(
                        modifier = Modifier
                            .border(0.5.dp, Color.LightGray)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .widthIn(min = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.selectedSize, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(painterResource(R.drawable.ic_arrow_down), null, modifier = Modifier.size(10.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Quantity
                    Box(modifier = Modifier.border(0.5.dp, Color.LightGray)) {
                        ProductQuantity(
                            quantity = item.quantity,
                            onIncrease = onIncrease,
                            onDecrease = onDecrease
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delete and Price Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.clickable { onDelete() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = colorResource(R.color.belle_black).copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "УДАЛИТЬ",
                            fontSize = 10.sp,
                            color = colorResource(R.color.belle_black).copy(alpha = 0.5f)
                        )
                    }

                    Text(
                        text = "${(product.price ?: 0) * item.quantity} ₽",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (showDivider) {
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
        }
    }
}