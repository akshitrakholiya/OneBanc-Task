package com.akshit.onebanc.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akshit.onebanc.R
import com.akshit.onebanc.databinding.FragmentCuisineInfoBinding
import com.akshit.onebanc.infra.utils.ConnectivityManager
import com.akshit.onebanc.models.CuisinesItem
import com.akshit.onebanc.utilities.ARG_CUISINE_INFO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CuisineInfoFragment : Fragment() {

    private lateinit var binding: FragmentCuisineInfoBinding
    private var cuisineInfo: CuisinesItem? = null

    @Inject
    lateinit var connectivityManager: ConnectivityManager

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}