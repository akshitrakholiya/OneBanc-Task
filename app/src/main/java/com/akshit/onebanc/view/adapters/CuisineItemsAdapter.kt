package com.akshit.onebanc.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.akshit.onebanc.databinding.ItemCuisineCardViewBinding
import com.akshit.onebanc.models.CuisinesItem

class CuisineItemsAdapter(
    private val cuisineItemClickListener: (CuisinesItem?) -> Unit
): RecyclerView.Adapter<CuisineItemsAdapter.CuisineItemsViewHolder>() {

    private val cuisineItems = mutableListOf<CuisinesItem?>()

    inner class CuisineItemsViewHolder(private val binding: ItemCuisineCardViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cuisine: CuisinesItem){
            binding.executePendingBindings()
            binding.cuisineData = cuisine

            binding.root.setOnClickListener {
                cuisineItemClickListener(cuisine)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineItemsViewHolder {
        val binding = ItemCuisineCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CuisineItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CuisineItemsViewHolder, position: Int) {
        cuisineItems.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return cuisineItems.size
    }

    fun addCuisineItemList(newCuisineItemList: List<CuisinesItem?>) {
        val startPosition = newCuisineItemList.size
        cuisineItems.addAll(newCuisineItemList)
        notifyItemRangeInserted(startPosition, newCuisineItemList.size)
    }

    fun clearCuisineItemList() {
        cuisineItems.clear()
        notifyDataSetChanged()
    }

    companion object {
        // Extension function to set up the RecyclerView
        fun RecyclerView.setupSmoothScroll(context: Context) {
            // Set up LinearLayoutManager
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            // Add LinearSnapHelper for snapping to items
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)


            // Optional: Add smooth scrolling behavior
            setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING)
        }
    }
}