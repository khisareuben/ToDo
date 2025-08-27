package com.example.todo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph
import com.example.todo.navigation.NavGraphM3
import com.example.todo.presentation.LocalTheme
import com.example.todo.presentation.MainScreen
import com.example.todo.presentation.darkThemeColors
import com.example.todo.presentation.lightThemeColor
import com.example.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        installSplashScreen()
        setContent {

            val isLightTheme = !isSystemInDarkTheme()

            // 🔧 Apply edge-to-edge system bar styling
            enableEdgeToEdge(
                statusBarStyle = if (isLightTheme) {
                    SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                } else {
                    SystemBarStyle.dark(Color.TRANSPARENT)
                },
                navigationBarStyle = if (isLightTheme) {
                    SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                } else {
                    SystemBarStyle.dark(Color.BLACK)
                }
            )

            ToDoTheme {
                val theme = if (isSystemInDarkTheme()) darkThemeColors else lightThemeColor
                CompositionLocalProvider(LocalTheme provides theme) {
                    NavGraphM3()
                }
            }
        }
    }
}



