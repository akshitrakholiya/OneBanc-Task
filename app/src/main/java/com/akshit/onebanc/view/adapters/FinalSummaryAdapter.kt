package com.akshit.onebanc.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshit.onebanc.databinding.ItemFinalSummaryBinding
import com.akshit.onebanc.models.ItemsItem

class FinalSummaryAdapter(
    private val finalCartItems:ArrayList<ItemsItem>
): RecyclerView.Adapter<FinalSummaryAdapter.FinalSummaryViewHolder>() {
    inner class FinalSummaryViewHolder(private val binding: ItemFinalSummaryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recipeInfo: ItemsItem){
            binding.recipeInfo = recipeInfo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalSummaryViewHolder {
        val binding = ItemFinalSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinalSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinalSummaryViewHolder, position: Int) {
        holder.bind(finalCartItems.get(position))
    }

    override fun getItemCount(): Int {
        return finalCartItems.size
    }
}