package com.akshit.onebanc.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshit.onebanc.databinding.ItemRecipeCardviewBinding
import com.akshit.onebanc.models.ItemsItem
import com.akshit.onebanc.view.interfaces.RecipeItemQtyListeners

class RecipeItemsAdapter(
    private val recipeItemQtyListeners: RecipeItemQtyListeners
) : RecyclerView.Adapter<RecipeItemsAdapter.RecipeItemsViewHolder>() {
    
    private val recipeItems = mutableListOf<ItemsItem?>()
    
    inner class RecipeItemsViewHolder(private val binding: ItemRecipeCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipeInfo: ItemsItem) {
            binding.recipeInfo = recipeInfo

            binding.ivPlus.setOnClickListener {
                recipeItemQtyListeners.removeFromCart(recipeInfo)
                recipeInfo.quantity++
                binding.recipeInfo = recipeInfo
                recipeItemQtyListeners.addToCart(recipeInfo)
                binding.executePendingBindings()
            }

            binding.ivMinus.setOnClickListener {
                if (recipeInfo.quantity > 0) {
                    recipeItemQtyListeners.removeFromCart(recipeInfo)
                    recipeInfo.quantity--
                    binding.recipeInfo = recipeInfo
                    if (recipeInfo.quantity!=0){
                        recipeItemQtyListeners.addToCart(recipeInfo)
                    }
                    binding.executePendingBindings()
                }else if (recipeInfo.quantity == 0){
                    recipeItemQtyListeners.removeFromCart(recipeInfo)
                }
            }
            
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemsViewHolder {
        val binding = ItemRecipeCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeItemsViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: RecipeItemsViewHolder, position: Int) {
        recipeItems.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return recipeItems.size
    }

    fun addNewRecipes(newRecipes: List<ItemsItem?>) {
        recipeItems.clear()
        recipeItems.addAll(newRecipes)
        notifyDataSetChanged()
    }
}