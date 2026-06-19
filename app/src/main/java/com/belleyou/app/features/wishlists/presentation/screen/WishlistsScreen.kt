package com.belleyou.app.features.wishlists.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.belleyou.app.R

@Composable
fun WishlistsScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = stringResource(id = R.string.wishlists_screen_title)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WishlistsScreenPreview() {
    WishlistsScreen()
}