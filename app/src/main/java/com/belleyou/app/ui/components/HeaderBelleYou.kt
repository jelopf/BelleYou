package com.belleyou.app.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.belleyou.app.R

@Composable
fun HeaderBelleYou(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.belle_you_home),
        contentDescription = "Логотип Belle You",
        modifier = modifier
            .width(87.dp)
            .height(25.dp),
        contentScale = ContentScale.Fit
    )
    Spacer(modifier = Modifier.height(8.dp))
}