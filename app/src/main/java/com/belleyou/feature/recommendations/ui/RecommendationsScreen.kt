package com.belleyou.feature.recommendations.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.belleyou.app.R
import com.belleyou.core.designsystem.components.layout.HeaderBelleYouWithBack
import com.belleyou.core.model.Product
import com.belleyou.feature.recommendations.RecommendationsViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun RecommendationsScreen(
    viewModel: RecommendationsViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onProductClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    RecommendationsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onSkip = viewModel::nextProduct,
        onFavorite = viewModel::toggleFavorite,
        onAddToCart = viewModel::addToCart,
        onDismissOnboarding = viewModel::dismissOnboarding
    )
}

@Composable
fun RecommendationsContent(
    uiState: RecommendationsUiState,
    onBackClick: () -> Unit,
    onProductClick: (String) -> Unit,
    onSkip: () -> Unit,
    onFavorite: () -> Unit,
    onAddToCart: () -> Unit,
    onDismissOnboarding: () -> Unit
) {
    val product = uiState.products.getOrNull(uiState.currentIndex)
    var actionTriggered by remember { mutableStateOf<RecommendationAction?>(null) }

    // Reset action trigger after animation
    LaunchedEffect(uiState.currentIndex) {
        actionTriggered = null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderBelleYouWithBack(
                onBackClick = onBackClick,
                title = "ПОДБОРКА",
                showDivider = false
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (product != null) {
                    AnimatedContent(
                        targetState = product,
                        transitionSpec = {
                            when (actionTriggered) {
                                RecommendationAction.SKIP -> {
                                    (slideInHorizontally { it } + fadeIn()) togetherWith
                                            (slideOutHorizontally { -it } + fadeOut())
                                }
                                RecommendationAction.CART -> {
                                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                                            (slideOutHorizontally { it } + fadeOut())
                                }
                                RecommendationAction.FAVORITE -> {
                                    (slideInVertically { it } + fadeIn()) togetherWith
                                            (slideOutVertically { -it } + fadeOut())
                                }
                                else -> {
                                    fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
                                }
                            }
                        },
                        label = "ProductTransition"
                    ) { currentProduct ->
                        SelectionCard(
                            product = currentProduct,
                            onProductClick = { onProductClick(currentProduct.id) },
                            onSkip = {
                                actionTriggered = RecommendationAction.SKIP
                                onSkip()
                            },
                            onFavorite = {
                                actionTriggered = RecommendationAction.FAVORITE
                                onFavorite()
                                onSkip() // Auto-next after favorite
                            },
                            onAddToCart = {
                                actionTriggered = RecommendationAction.CART
                                onAddToCart()
                                onSkip() // Auto-next after cart
                            },
                            isFavorite = uiState.favorites.contains(currentProduct.id)
                        )
                    }
                } else if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Text(
                        text = "Нет доступных товаров",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (uiState.showOnboarding) {
            SelectionOnboardingOverlay(onDismiss = onDismissOnboarding)
        }
    }
}

enum class RecommendationAction {
    SKIP, FAVORITE, CART
}

@Composable
fun SelectionCard(
    product: Product,
    onProductClick: () -> Unit,
    onSkip: () -> Unit,
    onFavorite: () -> Unit,
    onAddToCart: () -> Unit,
    isFavorite: Boolean
) {
    var offsetY by remember { mutableStateOf(0f) }
    val animatedOffsetY by animateFloatAsState(targetValue = offsetY, label = "SwipeOffset")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetY += dragAmount.y
                    },
                    onDragEnd = {
                        if (offsetY < -200f) {
                            onFavorite()
                        }
                        offsetY = 0f
                    }
                )
            }
            .offset { IntOffset(0, animatedOffsetY.roundToInt()) }
    ) {
        // Main Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 400f
                    )
                )
        )

        // Functional Zones (Invisible)
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSkip() })
            Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onFavorite() })
            Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onAddToCart() })
        }

        // Overlay Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = product.name.uppercase(),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Посмотреть карточку товара",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onProductClick() }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectionActionButton(
                    icon = Icons.Default.Close,
                    color = Color.Red,
                    onClick = onSkip
                )

                SelectionActionButton(
                    icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    color = colorResource(R.color.belle_blue_dark),
                    size = 80.dp,
                    iconSize = 32.dp,
                    onClick = onFavorite
                )

                SelectionActionButton(
                    icon = Icons.Outlined.ShoppingCart,
                    color = colorResource(R.color.belle_brown),
                    onClick = onAddToCart
                )
            }
        }
        
        // Feedback Heart Animation
        if (offsetY < -50f) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.White.copy(alpha = ((-offsetY / 400f).coerceIn(0f, 1f))),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(120.dp)
                    .scale(((-offsetY / 200f).coerceIn(0.5f, 1.5f)))
            )
        }
    }
}

@Composable
fun SelectionActionButton(
    icon: ImageVector,
    color: Color,
    size: Dp = 60.dp,
    iconSize: Dp = 24.dp,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = Color.White.copy(alpha = 0.9f),
        modifier = Modifier
            .size(size)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color.copy(alpha = 0.7f),
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
fun SelectionOnboardingOverlay(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismiss() }
    ) {
        // Dotted lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

            // Vertical lines
            drawLine(
                color = Color.White,
                start = Offset(width / 3, 0f),
                end = Offset(width / 3, height * 0.75f),
                strokeWidth = 2f,
                pathEffect = pathEffect
            )
            drawLine(
                color = Color.White,
                start = Offset(width * 2 / 3, 0f),
                end = Offset(width * 2 / 3, height * 0.75f),
                strokeWidth = 2f,
                pathEffect = pathEffect
            )

            // Horizontal line
            drawLine(
                color = Color.White,
                start = Offset(0f, height * 0.75f),
                end = Offset(width, height * 0.75f),
                strokeWidth = 2f,
                pathEffect = pathEffect
            )
        }

        // Instructions
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.75f)
            ) {
                OnboardingSection(
                    title = "ПРОПУСТИТЬ",
                    modifier = Modifier.weight(1f)
                )
                OnboardingSection(
                    title = "В ИЗБРАННОЕ",
                    modifier = Modifier.weight(1f),
                    showSwipeUp = true
                )
                OnboardingSection(
                    title = "В КОРЗИНУ",
                    modifier = Modifier.weight(1f)
                )
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "ОТКРЫТЬ КАРТОЧКУ ТОВАРА",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingSection(
    title: String,
    modifier: Modifier = Modifier,
    showSwipeUp: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showSwipeUp) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationsPreview() {
    RecommendationsContent(
        uiState = RecommendationsUiState(
            products = listOf(
                Product("1", "Топ геометрия формы", "W2100002", 7999, description = "", category = "Белый", imageUrl = "")
            ),
            showOnboarding = false
        ),
        onBackClick = {},
        onProductClick = {},
        onSkip = {},
        onFavorite = {},
        onAddToCart = {},
        onDismissOnboarding = {}
    )
}
