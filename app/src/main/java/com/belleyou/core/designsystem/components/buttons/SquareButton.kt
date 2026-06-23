package com.belleyou.core.designsystem.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.belleyou.app.R

@Composable
fun SquareButton(
    resId: Int,
    contentDescription: String,
    mainColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isPressed) mainColor else colorResource(id = R.color.white)
    val iconTint = if (isPressed) colorResource(id = R.color.white) else mainColor

    Box(
        modifier = Modifier
            .size(80.dp)
            .border(1.dp, mainColor, RoundedCornerShape(1.3.dp))
            .background(backgroundColor, RoundedCornerShape(1.3.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = modifier
        )
    }
}