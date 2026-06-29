package com.belleyou.core.designsystem.components.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Box(
        modifier = modifier
            .width(80.dp)
            .height(30.dp)
            .border(
                width = 1.dp,
                color = colorResource(R.color.belle_gray),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = onDecrease,
                modifier = Modifier.size(24.dp)
            ) {
                Text(
                    text = "−",
                    fontSize = 18.sp,
                    color = colorResource(R.color.belle_black)
                )
            }

            Text(
                text = quantity.toString(),
                fontSize = 14.sp,
                color = colorResource(R.color.belle_black)
            )

            IconButton(
                onClick = onIncrease,
                modifier = Modifier.size(24.dp)
            ) {
                Text(
                    text = "+",
                    fontSize = 16.sp,
                    color = colorResource(R.color.belle_black)
                )
            }
        }
    }
}