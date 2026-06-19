package com.belleyou.app.features.product.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
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
import com.belleyou.app.features.product.domain.model.Product
import com.belleyou.app.features.product.presentation.viewmodel.ProductDetailUiState
import com.belleyou.app.features.product.presentation.viewmodel.ProductDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = koinViewModel { parametersOf(productId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    ProductDetailContent(uiState = uiState)
}

@Composable
fun ProductDetailContent(
    uiState: ProductDetailUiState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ProductDetailUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ProductDetailUiState.Success -> {
                ProductDetailSuccess(product = uiState.product)
            }

            is ProductDetailUiState.Error -> {
                Text(
                    text = uiState.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ProductDetailSuccess(product: Product) {
    val scrollState = rememberScrollState()
    var selectedSize by remember { mutableStateOf("S") }
    var showDescription by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp)
        ) {
            // Logo Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.belle_you_home),
                    contentDescription = "Belle You Logo",
                    modifier = Modifier
                        .width(87.dp)
                        .height(25.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Product Image
            Image(
                painter = painterResource(id = R.drawable.slide), // Defaulting to slide as placeholder
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    color = colorResource(id = R.color.belle_black)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Color Variants
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    product.variantImages.take(2).forEach { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(52.dp, 70.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Size Selection Label
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Размер:",
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.belle_black)
                    )
                    Text(
                        text = "Таблица размеров",
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.belle_gray),
                        textDecoration = TextDecoration.None
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Sizes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    product.sizes.forEach { size ->
                        SizeItem(
                            size = size,
                            isSelected = size == selectedSize,
                            onClick = { selectedSize = size },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Accordion Sections
                Column(modifier = Modifier.fillMaxWidth()) {
                    AccordionItem("Описание и размеры", onClick = { showDescription = true })
                    AccordionItem("Состав и уход")
                    AccordionItem("Возврат")
                    AccordionItem("Наличие в магазинах")
                }
            }
        }

        // Add to Cart Button (Bottom)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { /* Add to cart action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.belle_blue).copy(alpha = 0.5f),
                    contentColor = colorResource(id = R.color.belle_black)
                ),
                shape = RectangleShape,
                elevation = null
            ) {
                Text(
                    text = "В КОРЗИНУ",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        if (showDescription) {
            ProductDescriptionOverlay(
                product = product,
                onClose = { showDescription = false }
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
            .clickable(enabled = false) {} // Consume clicks
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
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        HorizontalDivider(thickness = 0.5.dp, color = colorResource(id = R.color.belle_gray).copy(alpha = 0.3f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
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

@Preview(showBackground = true)
@Composable
fun ProductDetailPreview() {
    ProductDetailSuccess(
        product = Product(
            id = 1,
            name = "Лонгслив из хлопка",
            article = "BY001",
            price = 5990,
            rating = 4.8f,
            colors = listOf("Blue"),
            description = "Лонгслив из мягкого хлопка в рубчик — базовая вещь для вашего гардероба.",
            sizes = listOf("XS", "S", "M", "L", "XL"),
            variantImages = listOf(R.drawable.slide, R.drawable.slide),
            category = "Платья"
        )
    )
}