package com.belleyou.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.belleyou.app.navigation.AppNavHost
import com.belleyou.core.designsystem.theme.BelleYouTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BelleYouTheme {
                BelleYouRoot()
            }
        }
    }
}

// Корневая точка входа в Compose UI
@Composable
fun BelleYouRoot() {

    val navController = rememberNavController()

    AppNavHost(
        navController = navController
    )
}