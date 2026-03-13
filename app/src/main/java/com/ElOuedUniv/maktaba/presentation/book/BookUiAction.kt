package com.ElOuedUniv.maktaba.presentation.book

sealed interface BookUiAction {

    object RefreshBooks : BookUiAction

    object OnAddBookClick : BookUiAction

    object OnDismissAddBook : BookUiAction

    data class OnAddBookConfirm(
        val title: String,
        val isbn: String,
        val nbPages: Int
    ) : BookUiAction
}