package com.ElOuedUniv.maktaba.presentation.book.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ElOuedUniv.maktaba.data.model.ReadingStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(
    onBackClick: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BOOK DETAILS", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.book != null) {
                val book = uiState.book!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Hero Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .padding(24.dp)
                            .clip(RoundedCornerShape(8.dp))
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
                                modifier = Modifier.align(Alignment.Center).padding(16.dp),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = book.author,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                MetadataItem(
                                    icon = Icons.Default.MenuBook,
                                    label = "Reading Status:",
                                    value = when(book.status) {
                                        ReadingStatus.READING -> "${(book.currentPage.toFloat() / book.nbPages * 100).toInt()}% Reading"
                                        ReadingStatus.FINISHED -> "Finished"
                                        else -> "Not started"
                                    }
                                )
                                MetadataItem(
                                    icon = Icons.Default.Favorite,
                                    label = "Pages:",
                                    value = if (book.nbPages > 0) "${book.nbPages}" else "Not set"
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            
                            if (book.status == ReadingStatus.READING) {
                                LinearProgressIndicator(
                                    progress = { book.currentPage.toFloat() / book.nbPages },
                                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                MetadataItem(
                                    icon = Icons.Default.List,
                                    label = "ISBN:",
                                    value = book.isbn
                                )
                                MetadataItem(
                                    icon = Icons.Default.Numbers,
                                    label = "Pages:",
                                    value = if (book.nbPages > 0) "${book.nbPages}" else "Not set"
                                )
                            }
                        }
                    }
                }
            } else if (uiState.errorMessage != null) {
                Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            } else {
                Text("Book not found", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun MetadataItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
    }
}
