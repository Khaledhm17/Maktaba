package com.ElOuedUniv.maktaba.presentation.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.model.ReadingStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListView(
    onCategoriesClick: () -> Unit = {},
    onAddBookClick: () -> Unit = {},
    onBookClick: (String) -> Unit = {},
    viewModel: BookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MY LIBRARY", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                actions = {

                    IconButton(onClick = onCategoriesClick) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Categories")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBookClick,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Book", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (uiState.books.isEmpty()) {
                    EmptyBooksMessage(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.books) { book ->
                            BookCard(book = book, onClick = { onBookClick(book.isbn) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyBooksMessage(modifier: Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No books in your library yet.",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Tap the + button to add your first book!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BookCard(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (!book.imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = book.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = book.title,
                        modifier = Modifier.align(Alignment.Center).padding(8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ISBN:",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            text = book.isbn,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Status:",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = when(book.status) {
                                    ReadingStatus.READING -> "Reading"
                                    ReadingStatus.FINISHED -> "Finished"
                                    else -> "Not started"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = if (book.status == ReadingStatus.FINISHED) Icons.Default.CheckCircle else Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = if (book.status == ReadingStatus.FINISHED) Color(0xFF4CAF50) else Color(0xFF2196F3)
                            )
                        }
                    }
                }
            }
        }
    }
}
