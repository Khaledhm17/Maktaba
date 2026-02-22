package com.ElOuedUniv.maktaba.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ElOuedUniv.maktaba.data.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListView(
    books: List<Book>,
    isLoading: Boolean,
    onCategoriesClick: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maktaba - My Library") },
                actions = {
                    IconButton(onClick = onCategoriesClick) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Categories"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                if (books.isEmpty()) {
                    EmptyBooksMessage(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    BookList(
                        books = books,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun BookList(books: List<Book>, modifier: Modifier) {
    TODO("Not yet implemented")
}

@Composable
fun EmptyBooksMessage(modifier: Modifier) {
    TODO("Not yet implemented")
}