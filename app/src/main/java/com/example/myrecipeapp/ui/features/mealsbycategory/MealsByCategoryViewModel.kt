package com.example.myrecipeapp.ui.features.mealsbycategory

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.MealDetails
import com.example.myrecipeapp.data.apiService
import kotlinx.coroutines.launch

class CategoriesDetailViewModel : ViewModel () {
    private val _mealsDetailState = mutableStateOf(MealsDetailState())
    val mealsDetailState : State <MealsDetailState> = _mealsDetailState

    fun fetchMealsDetailsByCategory(categoryName : String){
        viewModelScope.launch {
            try {
                val response = apiService.getMealsByCategoryDetail(categoryName)
                Log.d("CategoriesDetailViewModel", "response from web = $response and $categoryName ${response.meals}")
                _mealsDetailState.value = _mealsDetailState.value.copy(
                    list = response.meals,
                    loading = false,
                    error = null
                )

            } catch (e: Exception){
                Log.e("CategoriesDetailViewModel", "Error occurred -> ${e.message}")
                _mealsDetailState.value = _mealsDetailState.value.copy(
                    loading = false,
                    error = "Error fetching the categories ${e.message}"
                )
            }
        }
    }

}

data class MealsDetailState(
    val loading: Boolean = true,
    val list: List<MealDetails> = emptyList(),
    val error: String? = null
)