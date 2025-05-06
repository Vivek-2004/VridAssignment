package com.vrid.assignment.data_models

import com.google.gson.annotations.SerializedName

data class BlogPost(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("link") val link: String,
    @SerializedName("title") val title: Title,
    @SerializedName("content") val content: Content,
    @SerializedName("excerpt") val excerpt: Excerpt,
    @SerializedName("jetpack_featured_media_url") val mediaUrl: String
) {
    data class Title(
        @SerializedName("rendered") val rendered: String
    )

    data class Excerpt(
        @SerializedName("rendered") val rendered: String
    )

    data class Content(
        @SerializedName("rendered") val rendered: String
    )
}
