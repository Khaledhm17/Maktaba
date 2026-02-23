package com.ElOuedUniv.maktaba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel(
    private val getBooksUseCase: GetBooksUseCase
) : ViewModel() {

    // Original full list
    private val _allBooks = MutableStateFlow<List<Book>>(emptyList())

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Combined filtered books (this is what UI observes)
    val books: StateFlow<List<Book>> =
        combine(_allBooks, _searchQuery) { books, query ->
            if (query.isBlank()) {
                books
            } else {
                books.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Total pages of current (filtered) books
    val totalPages: StateFlow<Int> =
        books
            .map { bookList ->
                bookList.sumOf { it.nbPages }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _allBooks.value = getBooksUseCase()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }

    // Called from Search Bar
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}