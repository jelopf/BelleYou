package com.belleyou.feature.wishlist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.cards.ProductCard
import com.belleyou.core.designsystem.components.layout.HeaderBelleYou
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderBelleYou()

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.favorites_screen_title),
                fontSize = 16.sp
            )

            Icon(
                painter = painterResource(R.drawable.ic_favorite_list_add),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {  },
                tint = colorResource(R.color.belle_brown)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        WishlistRow(
            wishlists = uiState.wishlists,
            selectedIndex = uiState.selectedWishlistIndex,
            onSelect = viewModel::selectWishlist
        )

        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 18.dp)
                .size(20.dp)
                .clickable {  },
            tint = colorResource(id = R.color.belle_brown)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.products.take(2).forEach { product ->
                ProductCard(
                    uiModel = product,
                    modifier = Modifier.weight(1f),
                    showSizeSelector = true,
                    showInCartButton = true,
                    showFavoriteIcon = true,
                    isFavorite = uiState.favorites.contains(product.product.id),
                    onFavoriteClick = {
                        viewModel.toggleFavorite(product.product.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun WishlistRow(
    wishlists: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 18.dp)
        ) {
            itemsIndexed(wishlists) { index, item ->
                WishlistButton(
                    title = item,
                    selected = index == selectedIndex,
                    onClick = { onSelect(index) }
                )
            }
        }

        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 4.dp)
                .size(12.dp)
                .clickable {
                    scope.launch { listState.animateScrollBy(-200f) }
                },
            tint = colorResource(R.color.belle_gray)
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp)
                .size(12.dp)
                .clickable {
                    scope.launch { listState.animateScrollBy(200f) }
                },
            tint = colorResource(R.color.belle_gray)
        )
    }
}

@Composable
private fun WishlistButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(
            1.dp,
            if (selected) colorResource(R.color.belle_brown)
            else colorResource(R.color.belle_gray)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (selected)
                colorResource(R.color.belle_brown)
            else colorResource(R.color.belle_black)
        ),
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 0.dp),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.height(30.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistsScreenPreview() {
    WishlistScreen()
}