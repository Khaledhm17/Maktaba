package com.ElOuedUniv.maktaba.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ElOuedUniv.maktaba.presentation.viewmodel.BookViewModel

@Composable
fun BookListScreen(
    viewModel: BookViewModel,
    onCategoriesClick: () -> Unit
) {

    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val totalPages by viewModel.totalPages.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Search by title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = "Total Books: ${books.size}",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = "Total Pages: $totalPages",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BookListView(
            books = books,
            isLoading = isLoading,
            onCategoriesClick = onCategoriesClick
        )
    }
}