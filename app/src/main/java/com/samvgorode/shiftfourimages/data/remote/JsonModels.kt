package com.samvgorode.shiftfourimages.data.remote

import com.google.gson.annotations.SerializedName

data class ImagesResponseItem(
    @SerializedName("id") var id: String? = null,
    @SerializedName("urls") var urls: Urls? = Urls(),
)

data class Urls(
    @SerializedName("small") var full: String? = null,
)

