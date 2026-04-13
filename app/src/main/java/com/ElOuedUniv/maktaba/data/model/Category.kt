package com.ElOuedUniv.maktaba.data.model

import androidx.compose.ui.graphics.vector.ImageVector

// TODO: Complete the Category data class implementation
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val iconRes: ImageVector
)