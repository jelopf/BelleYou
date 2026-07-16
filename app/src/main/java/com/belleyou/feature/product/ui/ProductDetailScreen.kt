package com.belleyou.feature.product.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.HeaderBelleYouWithBack
import com.belleyou.core.designsystem.components.layout.ProductHorizontalRow
import com.belleyou.core.model.Product
import com.belleyou.feature.product.ProductDetailViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: ProductDetailViewModel = koinViewModel { parametersOf(productId) }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onSelectSize = viewModel::selectSize,
        onToggleDescription = viewModel::toggleDescription,
        onCloseDescription = viewModel::closeDescription,
        onAddToCart = viewModel::addToCart,
        onFavoriteToggle = { viewModel.toggleFavorite() }
    )
}

@Composable
fun ProductDetailContent(
    uiState: ProductDetailUiState,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onSelectSize: (String) -> Unit,
    onToggleDescription: () -> Unit,
    onCloseDescription: () -> Unit,
    onAddToCart: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                Text(text = uiState.error, modifier = Modifier.align(Alignment.Center))
            }
            uiState.product != null -> {
                ProductDetailSuccess(
                    product = uiState.product,
                    uiState = uiState,
                    onBackClick = onBackClick,
                    onProductClick = onProductClick,
                    onSelectSize = onSelectSize,
                    onToggleDescription = onToggleDescription,
                    onAddToCart = onAddToCart,
                    onFavoriteToggle = onFavoriteToggle
                )
            }
        }
        
        if (uiState.showDescription && uiState.product != null) {
            ProductDescriptionOverlay(
                product = uiState.product,
                onClose = onCloseDescription
            )
        }
    }
}

@Composable
fun ProductDetailSuccess(
    product: Product,
    uiState: ProductDetailUiState,
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onSelectSize: (String) -> Unit,
    onToggleDescription: () -> Unit,
    onAddToCart: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState()
    
    val images = remember(product) {
        listOf(product.imageUrl) + product.variantImages
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp)
        ) {
            HeaderBelleYouWithBack(onBackClick = onBackClick, showDivider = false)

            // Image Pager
            Box(modifier = Modifier.fillMaxWidth().height(500.dp)) {
                HorizontalPager(
                    count = images.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Image(
                        painter = rememberAsyncImagePainter(images[page]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                // Favorite Button on Image
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(40.dp)
                        .clickable { onFavoriteToggle() }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = if (uiState.isFavorite) colorResource(R.color.belle_blue_dark) else Color.LightGray
                        )
                    }
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    activeColor = Color.White,
                    inactiveColor = Color.White.copy(alpha = 0.5f),
                    indicatorWidth = 6.dp,
                    indicatorHeight = 6.dp,
                    spacing = 8.dp
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.name.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "${product.price ?: 0} ₽",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "ЦВЕТ: ${uiState.selectedColor.uppercase()}", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    images.take(2).forEach { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp, 80.dp).border(0.5.dp, Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "РАЗМЕР", fontSize = 12.sp)
                    Text(
                        text = "Таблица размеров",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    product.sizes.forEach { size ->
                        SizeItem(
                            size = size,
                            isSelected = size == uiState.selectedSize,
                            onClick = { onSelectSize(size) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "О ТОВАРЕ", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { index ->
                        val isFilled = index < (product.rating ?: 0.0).toInt()
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (isFilled) Color.Black else Color.LightGray.copy(alpha = 0.5f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${product.reviewsCount ?: 0} отзывов", fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = product.description,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color.DarkGray
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "АРТ. ${product.article}", fontSize = 12.sp, color = Color.Gray)
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.modelParameters ?: "",
                    fontSize = 12.sp, 
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column {
                    AccordionItem(title = "ОПИСАНИЕ И РАЗМЕРЫ", onClick = onToggleDescription)
                    AccordionItem(title = "СОСТАВ И УХОД")
                    AccordionItem(title = "ВОЗВРАТ")
                    AccordionItem(title = "НАЛИЧИЕ В МАГАЗИНАХ")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RectangleShape,
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Text("Все товары из капсулы", fontSize = 12.sp, color = Color.Black)
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, modifier = Modifier.size(16.dp))
                    }
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RectangleShape,
                        border = BorderStroke(0.5.dp, Color.LightGray)
                    ) {
                        Text("Все кружево", fontSize = 12.sp, color = Color.Black)
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                ProductHorizontalRow(
                    title = "Идеально подходит",
                    products = uiState.matchingProducts,
                    favorites = uiState.recentlyViewed.map { it.id }.toSet(),
                    onProductClick = onProductClick,
                    onFavoriteClick = { },
                    onSeeAllClick = { }
                )

                Spacer(modifier = Modifier.height(32.dp))

                ProductHorizontalRow(
                    title = "Дополнить образ",
                    products = uiState.relatedProducts,
                    favorites = emptySet(),
                    onProductClick = onProductClick,
                    onFavoriteClick = { },
                    onSeeAllClick = { }
                )

                Spacer(modifier = Modifier.height(32.dp))

                ProductHorizontalRow(
                    title = "Вы недавно смотрели",
                    products = uiState.recentlyViewed,
                    favorites = emptySet(),
                    onProductClick = onProductClick,
                    onFavoriteClick = { },
                    onSeeAllClick = { }
                )
            }
        }

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.belle_blue).copy(alpha = 0.6f),
                    contentColor = colorResource(R.color.belle_black)
                ),
                shape = RectangleShape,
                elevation = null
            ) {
                Text("ДОБАВИТЬ В КОРЗИНУ", fontSize = 13.sp, letterSpacing = 1.sp)
            }
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
            .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {}
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ОПИСАНИЕ И РАЗМЕРЫ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = product.description, fontSize = 14.sp, lineHeight = 20.sp)
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
            .height(36.dp)
            .background(if (isSelected) colorResource(R.color.belle_blue).copy(alpha = 0.5f) else Color.Transparent)
            .border(width = 0.5.dp, color = Color.LightGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = size, fontSize = 12.sp, color = if (isSelected) Color.Black else Color.Gray)
    }
}

@Composable
fun AccordionItem(title: String, onClick: () -> Unit = {}) {
    Column {
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Normal)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailPreview() {
    ProductDetailContent(
        uiState = ProductDetailUiState(
            product = Product(
                id = 1,
                name = "Бюстгальтер итальянское кружево / femme fatale",
                article = "BY01-12345",
                price = 4999,
                description = "Бюстгальтер из итальянского кружева...",
                category = "Белье",
                imageUrl = "",
                sizes = listOf("XS", "S", "M", "L"),
                rating = 4.5,
                reviewsCount = 20,
                modelParameters = "Параметры модели: 175 см, 80/60/90 см. Размер на модели: XS"
            ),
            selectedSize = "S"
        ),
        onBackClick = {},
        onProductClick = {},
        onSelectSize = {},
        onToggleDescription = {},
        onCloseDescription = {},
        onAddToCart = {},
        onFavoriteToggle = {}
    )
}