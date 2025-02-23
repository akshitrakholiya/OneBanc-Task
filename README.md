#  📲 OneBanc Android Task

An Android app showcasing popular cuisine in the restaurant industry using the API. Features include infinite scrolling, cuisine info, and final summary with sgst & cgst.

## 🚀 Features
- **Details View**: Display cuisine info and a list of recipes.
- **Image Loading**: Efficiently load images with Glide and custom BindingAdapter.
- **Popular cuisine List**: Infinite scrolling using a custom pagination listener.

## ⚙ Technologies & Libraries
- **MVVM Architecture**: Separation of concerns with ViewModel and LiveData.
- **Retrofit 2**: Network requests to TMDB API.
- **Glide**: Image loading and caching.
- **DataBinding**: UI binding with custom adapters.

## 📷 Screenshots
<img src ="./images/ss_dashboard.png" width="200" />&nbsp;&nbsp;<img src ="./images/ss_cuisine_info.png" width="200" />
<img src ="./images/ss_final_summary.png" width="200" />


## 💻 Permissions
- Internet

## 📝 Image Loading with Glide and custom BindingAdapter

```
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
```


![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)
![ForTheBadge ANDROID](https://forthebadge.com/images/badges/built-for-android.svg)
![ForTheBadge GIT](https://forthebadge.com/images/badges/uses-git.svg)
