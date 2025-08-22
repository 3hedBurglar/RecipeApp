package com.example.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun RecipeApp(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Categories.route
    ){
        composable(
            Screen.Categories.route){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.MealDetailScreen.route, // This should resolve to "your_route_name/{strCategory}"
            arguments = listOf(navArgument("strCategory") { // <<< CHECK THIS NAME
                type = NavType.StringType
            })
            )
        {
            backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("strCategory")
            CategoryDetailScreen(strCategory = categoryId, navController = navController)
        }

    }
}