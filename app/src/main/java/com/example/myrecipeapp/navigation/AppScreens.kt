package com.example.myrecipeapp.navigation

sealed class Screen(val route: String) {
    object Categories : Screen("categories_screen")
    object MealDetailScreen : Screen("meals_detail_screen/{strCategory}") {
        fun createRoute(strCategory: String) = "meals_detail_screen/$strCategory"
    }
    // Add more screens here as your app grows
}