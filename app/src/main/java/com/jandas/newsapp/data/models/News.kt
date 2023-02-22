package com.jandas.newsapp.data.models

import com.google.gson.annotations.SerializedName

data class News(val id: String, val title: String, val images: Images = Images())

data class Images(@SerializedName("downsized") val downsized: ImageMeta = ImageMeta())

data class ImageMeta(val url: String = "", val height: String = "", val width: String = "") {
    fun getAspectRatio(): Float {
        if (height.isNotEmpty() && width.isNotEmpty()) {
            return width.toFloat() / height.toFloat()
        }
        // Setting default aspect ratio to 1
        return 1F
    }
}
