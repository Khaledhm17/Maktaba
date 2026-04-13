package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }
            is AddBookUiAction.OnIsbnChange -> {
                if (action.isbn.length <= 13 && action.isbn.all { it.isDigit() }) {
                    _uiState.update { it.copy(isbn = action.isbn) }
                    validateInputs()
                }
            }
            is AddBookUiAction.OnPagesChange -> {
                if (action.pages.all { it.isDigit() }) {
                    _uiState.update { it.copy(nbPages = action.pages) }
                    validateInputs()
                }
            }
            is AddBookUiAction.OnImageSelected -> {
                _uiState.update { it.copy(imageUri = action.uri) }
            }
            AddBookUiAction.OnAddClick -> {
                if (_uiState.value.isFormValid) {
                    addBook()
                }
            }
            AddBookUiAction.OnCancelClick -> {
                _uiState.update { it.copy(isSuccess = true) } // Navigate back
            }
        }
    }

    private fun validateInputs() {
        _uiState.update { state ->
            val titleError = if (state.title.isBlank()) "Title cannot be empty" else null
            val isbnError = if (state.isbn.length != 13) "ISBN must be exactly 13 digits" else null
            val pagesInt = state.nbPages.toIntOrNull()
            val pagesError = if (pagesInt == null || pagesInt <= 0) "Pages must be a positive number" else null
            
            state.copy(
                titleError = titleError,
                isbnError = isbnError,
                pagesError = pagesError,
                isFormValid = titleError == null && isbnError == null && pagesError == null
            )
        }
    }

    private fun addBook() {
        val currentState = _uiState.value
        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,
            imageUrl = currentState.imageUri?.toString() ?: ""
        )
        addBookUseCase(book)
        _uiState.update { it.copy(isSuccess = true) }
    }
}
