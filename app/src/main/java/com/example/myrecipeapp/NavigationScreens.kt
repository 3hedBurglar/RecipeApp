package com.example.myrecipeapp

sealed class Screen(val route: String) {
    object HomeScreen : Screen("recipe_screen")
    object CategoryDetailScreen : Screen("category_detail_screen/{strCategory}") {
        fun createRoute(strCategory: String) = "category_detail_screen/$strCategory"
    }
    // Add more screens here as your app grows
}