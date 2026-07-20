package com.belleyou.core.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.model.Product

@Composable
fun ProductInfoBlock(
    product: Product,
    showCartAction: Boolean = false,
    onCartClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = product.name.uppercase(),
            fontSize = 11.sp,
            lineHeight = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.belle_black)
        )

        // Actual Color indicators
        if (product.colorHexes.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                product.colorHexes.take(5).forEach { hex ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = try { Color(android.graphics.Color.parseColor(hex)) } catch(e: Exception) { Color.LightGray },
                                shape = CircleShape
                            )
                            .border(0.5.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
                    )
                }
                if (product.colorHexes.size > 5) {
                    Text("+${product.colorHexes.size - 5}", fontSize = 8.sp, color = Color.Gray)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${product.price ?: 0} ₽",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.belle_black)
                    )
                    
                    product.oldPrice?.let {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "$it ₽",
                            fontSize = 11.sp,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    }
                }
            }

            if (showCartAction) {
                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Add to cart",
                        tint = colorResource(R.color.belle_black),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}