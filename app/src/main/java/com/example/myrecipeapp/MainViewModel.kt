package com.example.myrecipeapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())

    val categoriesState : State <RecipeState> = _categoryState

    private val _randomRecipeState = mutableStateOf(randomMealState())
    val randomRecipeState : State<randomMealState> = _randomRecipeState

    init {
        fetchCategories()
//        fetchRandomRecipe()
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {

                val response = recipeService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
                Log.d("MainViewModel", "response from web = $response and catgories ${response.categories}")

            } catch (e: Exception){
                Log.e("MainViewModel", "Error occurred -> ${e.message}")
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error fetching the categories ${e.message}"
                )
            }
        }
    }

    private fun fetchRandomRecipe(){
        viewModelScope.launch {
            try{
                val response = recipeService.getRandomRecipe()
                _randomRecipeState.value = _randomRecipeState.value.copy(
                    list = response.meals,
                    loading = false,
                    error = null
                )
            }
            catch (e: Exception){
                Log.e("MainViewModel", "Error occurred -> ${e.message}")
                _randomRecipeState.value = _randomRecipeState.value.copy(
                    loading = false,
                    error = "Error fetching the categories ${e.message}"
                )
            }
        }
    }

    data class randomMealState(
        val loading : Boolean = true,
        val list : List<RandomRecipe> = emptyList(),
        val error : String? = null
    )

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}