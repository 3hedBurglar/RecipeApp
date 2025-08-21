package com.example.myrecipeapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CategoryDetailScreen(strCategory: String?, navController: NavController)
{ // Added NavController



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Details for Category ID: ${strCategory ?: "N/A"}")
        // You'll add a TopAppBar with a back button here later
    }
}