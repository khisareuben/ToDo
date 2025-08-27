package com.example.todo.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.presentation.AddScreen
import com.example.todo.presentation.EditScreen
import com.example.todo.presentation.MainScreen

@Composable
fun NavGraphM3() {


    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } }
    ) {
        composable(Screens.HomeScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Screens.AddScreen.route) {
            AddScreen(navController = navController)
        }
        composable("edit/{title}/{body}/{id}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val body = backStackEntry.arguments?.getString("body") ?: ""
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            EditScreen(navController = navController, title = title, body = body, id = id)
        }
    }
}