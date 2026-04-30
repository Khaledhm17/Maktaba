package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri

sealed class AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction()
    data class OnIsbnChange(val isbn: String) : AddBookUiAction()
    data class OnPagesChange(val pages: String) : AddBookUiAction()
    data class OnImageSelected(val uri: Uri?, val bytes: ByteArray?) : AddBookUiAction() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnImageSelected

            if (uri != other.uri) return false
            if (bytes != null) {
                if (other.bytes == null) return false
                if (!bytes.contentEquals(other.bytes)) return false
            } else if (other.bytes != null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = uri?.hashCode() ?: 0
            result = 31 * result + (bytes?.contentHashCode() ?: 0)
            return result
        }
    }
    object OnAddClick : AddBookUiAction()
}
