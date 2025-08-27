package com.example.todo.navigation

sealed class Screens(val route: String) {
    data object HomeScreen: Screens("home")
    data object AddScreen: Screens("add")
}