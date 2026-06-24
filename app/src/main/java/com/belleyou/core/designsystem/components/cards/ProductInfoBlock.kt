package com.belleyou.core.designsystem.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.model.ProductUiModel


@Composable
fun ProductInfoBlock(product: ProductUiModel) {

    Column(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {
                product.product.oldPrice?.let {
                    Text(
                        text = "$it ₽",
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(Modifier.width(4.dp))
                }

                Text(
                    text = "${product.product.price} ₽",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (product.product.oldPrice != null)
                        colorResource(R.color.belle_red)
                    else
                        colorResource(R.color.black)
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "${product.product.rating} ★",
                fontSize = 14.sp,
                lineHeight = 14.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(product.product.brand, fontSize = 14.sp)
            Text(
                "(${product.product.reviewsCount})",
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = colorResource(R.color.belle_gray)
            )
        }

        Text(
            text = product.product.name,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}