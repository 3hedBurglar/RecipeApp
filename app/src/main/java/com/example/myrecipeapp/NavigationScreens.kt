package com.example.myrecipeapp

sealed class Screen(val route: String) {
    object RecipeScreen : Screen("recipe_screen")
    object CategoryDetailScreen : Screen("category_detail_screen/{categoryId}") {
        fun createRoute(categoryId: String) = "category_detail_screen/$categoryId"
    }
    // Add more screens here as your app grows
}