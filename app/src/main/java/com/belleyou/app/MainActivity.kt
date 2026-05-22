package com.belleyou.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.belleyou.app.navigation.AppNavHost
import com.belleyou.app.ui.theme.BelleYouTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BelleYouTheme {
                AppNavHost()
            }
        }
    }
}