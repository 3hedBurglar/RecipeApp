package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RecipeApp(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.RecipeScreen.route
    ){
        composable(Screen.RecipeScreen.route){
            RecipeScreen(navController = navController)
        }
        composable(route = Screen.CategoryDetailScreen.route)
        {
            backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("strCategory")
            CategoryDetailScreen(strCategory = categoryId, navController = navController)
        }

    }
}