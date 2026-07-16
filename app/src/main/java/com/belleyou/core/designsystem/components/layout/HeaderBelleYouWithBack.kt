package com.belleyou.core.designsystem.components.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R

@Composable
fun HeaderBelleYouWithBack(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    showDivider: Boolean = true,
    trailingContent: @Composable RowScope.() -> Unit = {
        // Default empty placeholder to keep logo centered
        Spacer(modifier = Modifier.width(40.dp))
    }
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() },
                tint = Color.Black
            )

            if (title != null) {
                Text(
                    text = title.uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp,
                    color = Color.Black
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.belle_you_home),
                    contentDescription = "Belle You",
                    modifier = Modifier
                        .width(87.dp)
                        .height(25.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                trailingContent()
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.belle_under_header)
            )
        }
    }
}