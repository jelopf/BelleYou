package com.belleyou.core.designsystem.components.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R

@Composable
fun ProductQuantity(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(36.dp)
            .width(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onDecrease() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                fontSize = 18.sp,
                color = colorResource(R.color.belle_black)
            )
        }

        VerticalDivider(thickness = 0.5.dp, color = Color.LightGray)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                fontSize = 14.sp,
                color = colorResource(R.color.belle_black)
            )
        }

        VerticalDivider(thickness = 0.5.dp, color = Color.LightGray)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onIncrease() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 16.sp,
                color = colorResource(R.color.belle_black)
            )
        }
    }
}