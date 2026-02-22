package com.ElOuedUniv.maktaba.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ElOuedUniv.maktaba.presentation.viewmodel.CategoryViewModel

@Composable
fun CategoryListScreen(
    viewModel: CategoryViewModel,
    onBackClick: () -> Unit
) {

    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    CategoryListView(
        categories = categories,
        isLoading = isLoading,
        onBackClick = onBackClick
    )
}