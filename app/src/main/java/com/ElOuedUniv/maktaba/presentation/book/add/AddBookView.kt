package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookView(
    onBackClick: () -> Unit,
    viewModel: AddBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onAction(AddBookUiAction.OnImageSelected(uri)) }
    )

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ADD BOOK", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                actions = {
                    TextButton(onClick = { viewModel.onAction(AddBookUiAction.OnCancelClick) }) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { viewModel.onAction(AddBookUiAction.OnAddClick) },
                        enabled = uiState.isFormValid
                    ) {
                        Text("Confirm")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image Picker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.imageUri != null) {
                    AsyncImage(
                        model = uiState.imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("ADD COVER IMAGE", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.onAction(AddBookUiAction.OnTitleChange(it)) },
                label = { Text("Book Title") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.titleError != null,
                supportingText = uiState.titleError?.let { { Text(it) } }
            )

            OutlinedTextField(
                value = uiState.isbn,
                onValueChange = { viewModel.onAction(AddBookUiAction.OnIsbnChange(it)) },
                label = { Text("ISBN") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.isbnError != null,
                supportingText = {
                    if (uiState.isbnError != null) {
                        Text(uiState.isbnError!!)
                    } else {
                        Text("${uiState.isbn.length}/13 digits")
                    }
                }
            )

            OutlinedTextField(
                value = uiState.nbPages,
                onValueChange = { viewModel.onAction(AddBookUiAction.OnPagesChange(it)) },
                label = { Text("Pages") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.pagesError != null,
                supportingText = uiState.pagesError?.let { { Text(it) } },
                placeholder = { Text("Not set") }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { viewModel.onAction(AddBookUiAction.OnAddClick) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = uiState.isFormValid
            ) {
                Text("Confirm")
            }

            OutlinedButton(
                onClick = { viewModel.onAction(AddBookUiAction.OnCancelClick) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}
