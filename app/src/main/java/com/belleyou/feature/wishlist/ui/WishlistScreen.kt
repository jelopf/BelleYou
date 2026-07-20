package com.belleyou.feature.wishlist.ui

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.lazy.items as lazyItems
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.ProductHorizontalRow
import com.belleyou.core.model.Product
import com.belleyou.feature.wishlist.WishlistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = koinViewModel(),
    onProductClick: (String) -> Unit = {},
    onGoToCatalog: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showCreateDialog by remember { mutableStateOf(false) }

    WishlistScreenContent(
        uiState = uiState,
        onProductClick = onProductClick,
        onFavoriteToggle = viewModel::toggleFavorite,
        onAddToCart = viewModel::addToCart,
        onSelectWishlist = viewModel::selectWishlist,
        onGoToCatalog = onGoToCatalog,
        onShareClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, viewModel.getShareText())
            }
            context.startActivity(Intent.createChooser(shareIntent, "Поделиться вишлистом"))
        },
        onCreateWishlistClick = { showCreateDialog = true },
        onDeleteWishlist = viewModel::deleteWishlist,
        onMoveProduct = viewModel::moveProduct
    )

    if (showCreateDialog) {
        CreateWishlistDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { name ->
                viewModel.createWishlist(name)
                showCreateDialog = false
            }
        )
    }
}

@Composable
fun WishlistScreenContent(
    uiState: WishlistUiState,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onSelectWishlist: (String) -> Unit,
    onGoToCatalog: () -> Unit,
    onShareClick: () -> Unit,
    onCreateWishlistClick: () -> Unit,
    onDeleteWishlist: (String) -> Unit,
    onMoveProduct: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Custom Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "ИЗБРАННОЕ",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onShareClick() },
                    tint = colorResource(id = R.color.belle_black)
                )
            }
        }

        if (uiState.products.isEmpty() && uiState.selectedWishlistName == "ИЗБРАННОЕ") {
            EmptyWishlistContent(
                recommendedProducts = uiState.recommendedProducts,
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteToggle = onFavoriteToggle,
                onGoToCatalog = onGoToCatalog
            )
        } else {
            FilledWishlistContent(
                uiState = uiState,
                onProductClick = onProductClick,
                onFavoriteToggle = onFavoriteToggle,
                onAddToCart = onAddToCart,
                onSelectWishlist = onSelectWishlist,
                onCreateWishlistClick = onCreateWishlistClick,
                onDeleteWishlist = onDeleteWishlist,
                onMoveProduct = onMoveProduct
            )
        }
    }
}

@Composable
private fun FilledWishlistContent(
    uiState: WishlistUiState,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onAddToCart: (String) -> Unit,
    onSelectWishlist: (String) -> Unit,
    onCreateWishlistClick: () -> Unit,
    onDeleteWishlist: (String) -> Unit,
    onMoveProduct: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Wishlists Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ВИШЛИСТЫ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 1.sp
            )
            Text(
                text = "Добавить вишлист",
                fontSize = 12.sp,
                color = colorResource(id = R.color.belle_black),
                modifier = Modifier.clickable { onCreateWishlistClick() }
            )
        }

        // Wishlists Selector
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            lazyItems(uiState.wishlists) { title ->
                val isSelected = title == uiState.selectedWishlistName
                Box {
                    OutlinedButton(
                        onClick = { onSelectWishlist(title) },
                        shape = RectangleShape,
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = if (isSelected) colorResource(id = R.color.belle_blue_dark) else Color.LightGray
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) colorResource(id = R.color.belle_blue_dark).copy(alpha = 0.5f) else Color.Transparent,
                            contentColor = colorResource(id = R.color.belle_black)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = title, fontSize = 12.sp)
                    }
                }
            }
        }
        
        // Wishlist Actions (Delete)
        if (uiState.selectedWishlistName != "ИЗБРАННОЕ") {
            TextButton(
                onClick = { onDeleteWishlist(uiState.selectedWishlistName) },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(Icons.Default.Delete, null, modifier = Modifier.size(16.dp), tint = Color.Red)
                Spacer(Modifier.width(4.dp))
                Text("Удалить этот вишлист", color = Color.Red, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.products.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("В этом списке пока нет товаров", color = Color.Gray)
            }
        } else {
            // Product Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                gridItems(uiState.products) { product ->
                    Box {
                        var showMenu by remember { mutableStateOf(false) }
                        
                        Column {
                            ProductCard(
                                product = product,
                                isFavorite = true,
                                showFavoriteIcon = true,
                                showInCartButton = true,
                                onClick = { onProductClick(product.id) },
                                onFavoriteClick = { onFavoriteToggle(product.id) },
                                onAddToCartClick = { onAddToCart(product.id) }
                            )
                        }
                        
                        // Move to another wishlist button
                        IconButton(
                            onClick = { showMenu = true },
                            modifier = Modifier.align(Alignment.TopStart).padding(4.dp)
                        ) {
                            Icon(Icons.Default.MoreVert, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        }
                        
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            Text("Переместить в:", modifier = Modifier.padding(8.dp), fontSize = 12.sp, color = Color.Gray)
                            uiState.wishlists.filter { it != uiState.selectedWishlistName }.forEach { target ->
                                DropdownMenuItem(
                                    text = { Text(target) },
                                    onClick = {
                                        onMoveProduct(product.id, target)
                                        showMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateWishlistDialog(
    onDismiss: () -> Unit,
    onCreate: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Новый вишлист") },
        text = {
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Название (например, Подарки)") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        },
        confirmButton = {
            TextButton(onClick = { if (name.isNotBlank()) onCreate(name) }) {
                Text("СОЗДАТЬ", color = colorResource(R.color.belle_black))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("ОТМЕНА", color = Color.Gray)
            }
        },
        shape = RectangleShape
    )
}

@Composable
private fun EmptyWishlistContent(
    recommendedProducts: List<Product>,
    favorites: Set<String>,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onGoToCatalog: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "ВЫ ЕЩЕ НИЧЕГО НЕ ДОБАВИЛИ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Добавляйте понравившиеся товары в Избранное, чтобы посмотреть или купить их позже",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onGoToCatalog,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.belle_blue).copy(alpha = 0.5f),
                contentColor = colorResource(id = R.color.belle_black)
            ),
            shape = RectangleShape,
            elevation = null
        ) {
            Text(text = "ПЕРЕЙТИ В КАТАЛОГ", fontSize = 14.sp, letterSpacing = 1.sp)
        }
        
        Spacer(modifier = Modifier.height(60.dp))
        
        if (recommendedProducts.isNotEmpty()) {
            ProductHorizontalRow(
                title = "Рекомендуем",
                products = recommendedProducts,
                favorites = favorites,
                onProductClick = onProductClick,
                onFavoriteClick = onFavoriteToggle,
                onSeeAllClick = onGoToCatalog
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistScreenEmptyPreview() {
    WishlistScreenContent(
        uiState = WishlistUiState(
            products = emptyList(),
            recommendedProducts = listOf(
                Product("1", "Товар 1", "123", 1000, description = "", category = "", imageUrl = "")
            )
        ),
        onProductClick = {},
        onFavoriteToggle = {},
        onAddToCart = {},
        onSelectWishlist = {},
        onGoToCatalog = {},
        onShareClick = {},
        onCreateWishlistClick = {},
        onDeleteWishlist = {},
        onMoveProduct = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
fun WishlistScreenFilledPreview() {
    WishlistScreenContent(
        uiState = WishlistUiState(
            wishlists = listOf("ИЗБРАННОЕ", "НА ДР", "НА 8 МАРТА"),
            selectedWishlistName = "ИЗБРАННОЕ",
            products = listOf(
                Product("1", "Брюки из батиста", "123", 12999, description = "", category = "", imageUrl = ""),
                Product("2", "Трусы-слипы", "456", 599, description = "", category = "", imageUrl = "")
            )
        ),
        onProductClick = {},
        onFavoriteToggle = {},
        onAddToCart = {},
        onSelectWishlist = {},
        onGoToCatalog = {},
        onShareClick = {},
        onCreateWishlistClick = {},
        onDeleteWishlist = {},
        onMoveProduct = { _, _ -> }
    )
}