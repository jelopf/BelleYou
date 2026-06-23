package com.belleyou.feature.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import com.belleyou.core.designsystem.components.cards.ProductCardCart

@Composable
fun CartScreen() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderBelleYou(
            modifier = Modifier.padding(top = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .alpha(0.3f)
                .height(0.6.dp),
            color = colorResource(R.color.belle_brown)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.cart_screen_title),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "(2)",
                color = colorResource(id = R.color.gray_text),
                fontSize = 16.sp
            )
        }

        //Карточки
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            ProductCardCart()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp, horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ИТОГО",
                    fontSize = 12.sp
                )
                Text(
                    text = "77777 р",
                    fontSize = 12.sp
                )
            }
            Button(
                onClick = { /* действие */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.belle_blue),
                    contentColor = colorResource(id = R.color.belle_blue_dark)),
                shape = RoundedCornerShape(3.dp),
                contentPadding = PaddingValues(vertical = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "выбрать магазин".uppercase(),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.belle_black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen()
}