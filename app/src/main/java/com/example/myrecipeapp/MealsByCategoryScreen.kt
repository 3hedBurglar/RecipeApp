package com.example.myrecipeapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun CategoryDetailScreen(strCategory: String?, navController: NavController)
{ // Added NavController
    val categoryDetailViewModel : CategoriesDetailViewModel = viewModel()
    val viewState by categoryDetailViewModel.mealsDetailState

    LaunchedEffect(key1 = strCategory) { // Re-launch if strCategory changes
        if (!strCategory.isNullOrBlank()) {
            Log.d("CategoryDetailScreen", "LaunchedEffect: Fetching meals for category: $strCategory")
            categoryDetailViewModel.fetchMealsDetailsByCategory(strCategory)
        } else {
            Log.d("CategoryDetailScreen", "LaunchedEffect: strCategory is null or blank. Not fetching.")
            // Optionally, update viewState to show an error or empty state immediately
            // categoryDetailViewModel.setEmptyOrErrorState("Category not provided")
        }
    }

    val modifier: Modifier = Modifier

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (viewState.loading && viewState.list.isEmpty()) { // Show loader only if list is empty
            CircularProgressIndicator(modifier.align(Alignment.Center))
        } else if (viewState.error != null) {
            Text("Error: ${viewState.error}")
        } else if (viewState.list.isEmpty()) {
            Text("No meals found for ${strCategory ?: "this category"}.")
        } else {
            // Display your list of meals
//            Text("Meals for ${strCategory ?: "category"}:")
            MealList(meals = viewState.list)
        }
    }
}

@Composable
fun MealList(meals: List<MealDetails>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp), // Let the grid adapt column count based on screen width
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp), // Overall padding for the grid
        verticalArrangement = Arrangement.spacedBy(8.dp),  // Space between rows
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(meals,
            key = { meal -> meal.idMeal })
        {
            mealData ->
            MealListItem(
                meal = mealData
            )
        }
    }
}

@Composable
fun MealListItem(
    meal: MealDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), // Padding around each card
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Subtle elevation
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Column(
            modifier = Modifier.fillMaxWidth() // Column takes the full width of the card
        ) {
            // --- IMAGE ---
            val imagePainter = rememberAsyncImagePainter(
                model = meal.strMealThumb,
            )

            Image(
                painter = imagePainter,
                contentDescription = "${meal.strMeal} thumbnail", // More descriptive for accessibility
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f) // Adjust if your images are mostly square (1f/1f) or other
                // .clip(MaterialTheme.shapes.medium) // Optional: clip if image is flush with top edges
                ,
                contentScale = ContentScale.Crop
            )

            // --- CONTENT AREA (Name & Description) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp) // Padding inside the card, below the image
            ) {
                // --- NAME ---
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface // Good contrast
                )
            }
        }
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun MealListItemPreview() {
    // Wrap with your app's theme if it defines custom typography/shapes for accurate preview
    // MyRecipeAppTheme {
    MealListItem(
        meal = MealDetails(
            idMeal = "1".toInt(),
            strMeal = "Spaghetti Carbonara Special with Extra Long Name",
            strMealThumb = "", // Leave empty to see placeholder/error
        )
    )
    // }
}

@Preview(showBackground = true)
@Composable
fun MealListItemNoDescriptionPreview() {
    // MyRecipeAppTheme {
    MealListItem(
        meal = MealDetails(
            idMeal = "2".toInt(),
            strMeal = "Quick Salad",
            strMealThumb = "",
        )
    )
    // }
}