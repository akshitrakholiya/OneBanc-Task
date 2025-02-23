package com.akshit.onebanc.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.akshit.onebanc.R
import com.bumptech.glide.Glide

object BindingUtils {
    @JvmStatic
    @BindingAdapter("load_image")
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url) // image url
            .placeholder(R.drawable.placeholder_dark) // any placeholder to load at start
            .error(R.drawable.placeholder_dark) // any image in case of error
            .centerCrop()
            .into(imageView)
    }
}