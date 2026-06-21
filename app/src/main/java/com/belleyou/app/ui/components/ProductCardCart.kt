package com.belleyou.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R

@Composable
fun ProductCardCart(

){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        //Заглушка вместо изображения
        Box(
            modifier = Modifier
                .size(66.dp, 100.dp)
                .background(colorResource(id = R.color.belle_blue))
        )
        //Блок с текстом и кнопками
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            //Тексты
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cart_screen_title),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "7777 р",
                    fontSize = 12.sp
                )
            }
            Row(){
                Text(
                    text = "арт.",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.gray_text)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "88005553535",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.gray_text)
                )
            }
            Row(){
                Text(
                    text = "Цвет:",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.gray_text)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "серо-буро-малиновый",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.gray_text)
                )
            }
            //Кнопки
            Row(
                modifier = Modifier
                    .padding(top = 13.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .border(
                                width = 0.3.dp,
                                color = colorResource(id = R.color.gray_text)
                            )
                            .size(80.dp, 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                    }
                    //Количество товара
                    Row(
                        modifier = Modifier
                            .border(
                                width = 0.3.dp,
                                color = colorResource(id = R.color.gray_text)
                            )
                            .padding(horizontal = 2.dp)
                            .size(80.dp, 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = { /* действие */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Transparent),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(vertical = 0.dp),
                            modifier = Modifier.size(14.dp,25.dp)
                        ) {
                            Text(
                                text = "-",
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.belle_black)
                            )
                        }
                        Text(
                            text = "3",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.belle_black)
                        )
                        Button(
                            onClick = { /* действие */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Transparent),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(vertical = 0.dp),
                            modifier = Modifier.size(14.dp,25.dp)
                        ) {
                            Text(
                                text = "+",
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.belle_black)
                            )
                        }
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart_delete_item),
                    contentDescription = "Удалить",
                    modifier = Modifier
                        .clickable { },
                    tint = colorResource(R.color.belle_brown)
                )
            }
        }
    }
}