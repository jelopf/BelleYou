package com.belleyou.feature.product.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import com.belleyou.core.model.Product
import com.belleyou.core.model.ProductUiModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = koinViewModel { parametersOf(productId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    ProductDetailContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onSelectSize = viewModel::selectSize,
        onToggleDescription = viewModel::toggleDescription,
        onCloseDescription = viewModel::closeDescription
    )
}

@Composable
fun ProductDetailContent(
    uiState: ProductDetailUiState,
    onBackClick: () -> Unit,
    onSelectSize: (String) -> Unit,
    onToggleDescription: () -> Unit,
    onCloseDescription: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        when {
            uiState.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.product != null -> {
                ProductDetailSuccess(
                    product = uiState.product,
                    selectedSize = uiState.selectedSize,
                    showDescription = uiState.showDescription,
                    onBackClick = onBackClick,
                    onSelectSize = onSelectSize,
                    onToggleDescription = onToggleDescription,
                    onCloseDescription = onCloseDescription
                )
            }
        }
    }
}

@Composable
fun ProductDetailSuccess(
    product: ProductUiModel,
    selectedSize: String,
    showDescription: Boolean,
    onBackClick: () -> Unit,
    onSelectSize: (String) -> Unit,
    onToggleDescription: () -> Unit,
    onCloseDescription: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp)
        ) {

            // ===== HEADER =====
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                HeaderBelleYou()

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colorResource(id = R.color.belle_gray)
                    )
                }
            }

            // ===== IMAGE =====
            Image(
                painter = painterResource(id = R.drawable.slide),
                contentDescription = product.product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ===== INFO =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                Text(
                    text = product.product.name,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ===== COLORS =====
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    product.images.take(2).forEach { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier.size(52.dp, 70.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ===== SIZE LABEL =====
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Размер:", fontSize = 12.sp)

                    Text(
                        text = "Таблица размеров",
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.belle_gray)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ===== SIZES =====
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    product.sizes.forEach { size ->
                        SizeItem(
                            size = size,
                            isSelected = size == selectedSize,
                            onClick = { onSelectSize(size) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ===== ACCORDION =====
                Column {
                    AccordionItem(
                        title = "Описание и размеры",
                        onClick = onToggleDescription
                    )

                    AccordionItem("Состав и уход")
                    AccordionItem("Возврат")
                    AccordionItem("Наличие в магазинах")
                }
            }
        }

        // ===== ADD TO CART =====
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Button(
                onClick = { /* add to cart */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.belle_blue).copy(alpha = 0.5f),
                    contentColor = colorResource(id = R.color.belle_black)
                )
            ) {
                Text("В КОРЗИНУ")
            }
        }

        // ===== OVERLAY =====
        if (showDescription) {
            ProductDescriptionOverlay(
                product = product.product,
                onClose = onCloseDescription
            )
        }
    }
}

@Composable
fun ProductDescriptionOverlay(
    product: Product,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {} // Consume clicks
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ОПИСАНИЕ И РАЗМЕРЫ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = colorResource(id = R.color.belle_black)
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colorResource(id = R.color.belle_black),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = product.description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = colorResource(id = R.color.belle_black)
            )
        }
    }
}

@Composable
fun SizeItem(
    size: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                if (isSelected) colorResource(id = R.color.belle_blue).copy(alpha = 0.5f)
                else Color.Transparent
            )
            .border(
                width = 0.5.dp,
                color = colorResource(id = R.color.belle_gray).copy(alpha = 0.3f)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size,
            fontSize = 12.sp,
            color = colorResource(id = R.color.belle_black)
        )
    }
}

@Composable
fun AccordionItem(title: String, onClick: () -> Unit = {}) {
    Column {
        HorizontalDivider(thickness = 0.5.dp, color = colorResource(id = R.color.belle_gray).copy(alpha = 0.3f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = colorResource(id = R.color.belle_black)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colorResource(id = R.color.belle_gray).copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProductDetailPreview() {
//    ProductDetailSuccess(
//        onBackClick = {},
//        product = Product(
//            id = 1,
//            name = "Лонгслив из хлопка Одежда для отдыха / Cruise черно-молочная полоска",
//            article = "BY001",
//            price = 5990,
//            oldPrice = 7990,
//            rating = 4.8f,
//            reviewsCount = 145,
//            brand = "SELA",
//            description = "Лонгслив из мягкого хлопка в рубчик — базовая вещь для вашего гардероба. Модель с глубоким круглым вырезом и длинными рукавами.\n\n• Облегающий крой\n• Мягкий трикотаж в рубчик",
//            category = "Платья"
//        )
//    )
//}