package com.example.myrecipeapp

data class Category (
    val idCategory : Int,
    val strCategory : String,
    val strCategoryThumb : String,
    val strCategoryDescription : String

)

data class CategoryResponse(
    val categories : List<Category>
)

data class MealDetails(
    val idMeal : Int,
    val strMeal : String,
    val strMealThumb : String
)

data class MealsResponse(
    val meals : List<MealDetails>
)