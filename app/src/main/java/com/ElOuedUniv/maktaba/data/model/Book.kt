package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("isbn")
    val isbn: String,
    @SerialName("title")
    val title: String,
    @SerialName("nbPages") // قمنا بتغييرها من nb_pages إلى nbPages لتطابق جدولك
    val nbPages: Int,
    @SerialName("imageUrl") // قمنا بتغييرها من image_url إلى imageUrl لتطابق جدولك
    val imageUrl: String? = null
)
