package com.akshit.onebanc.view.interfaces

import com.akshit.onebanc.models.ItemsItem

interface RecipeItemQtyListeners {

    fun addToCart(recipeItem: ItemsItem)

    fun removeFromCart(recipeItem: ItemsItem)
}