package com.akshit.onebanc.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.akshit.onebanc.databinding.FragmentCuisineInfoBinding
import com.akshit.onebanc.models.CuisinesItem
import com.akshit.onebanc.models.ItemsItem
import com.akshit.onebanc.utilities.ARG_CUISINE_INFO
import com.akshit.onebanc.view.adapters.RecipeItemsAdapter
import com.akshit.onebanc.view.interfaces.RecipeItemQtyListeners
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CuisineInfoFragment : Fragment(), RecipeItemQtyListeners {

    private lateinit var binding: FragmentCuisineInfoBinding
    private var cuisineInfo: CuisinesItem? = null

    private lateinit var recipeItemsAdapter: RecipeItemsAdapter
    private var finalCartItems:MutableSet<ItemsItem> = mutableSetOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            cuisineInfo = it.getParcelable(ARG_CUISINE_INFO, CuisinesItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuisineInfoBinding.inflate(inflater, container, false)
        binding.cuisineInfo = cuisineInfo
        setupRecipeRecyclerView()
        setupViewClickListeners()
        return binding.root
    }

    private fun setupViewClickListeners() {
        binding.ivBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivShopping.setOnClickListener {
            if (finalCartItems.isEmpty()){
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Items in cart ${finalCartItems.map { it.name }}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecipeRecyclerView() {
        recipeItemsAdapter = RecipeItemsAdapter(this)
        binding.rvRecipeList.apply {
            adapter = recipeItemsAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sortedItems = cuisineInfo?.items?.sortedByDescending { it?.rating?.toDoubleOrNull() }
        sortedItems?.let { recipeItemsAdapter.addNewRecipes(it) }
    }

    override fun addToCart(recipeItem: ItemsItem) {
        finalCartItems.add(recipeItem)
    }

    override fun removeFromCart(recipeItem: ItemsItem) {
        finalCartItems.remove(recipeItem)
    }

}