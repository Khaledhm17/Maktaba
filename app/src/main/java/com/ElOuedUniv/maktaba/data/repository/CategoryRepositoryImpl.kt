package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.R
import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Phonelink
import androidx.compose.material.icons.filled.Security

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {

    private val _categoriesList = listOf(
        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding",
            iconRes = Icons.Default.Phonelink
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures",
            iconRes = Icons.Default.Language
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management",
            iconRes = Icons.Default.FileCopy
        )
        ,
        Category(
            id = "4",
            name = "Cyber security",
            description = "Books about database design and management",
            iconRes = Icons.Default.Security
        )

    )

    private val categoriesFlow = MutableSharedFlow<List<Category>>(replay = 1).apply {
        tryEmit(_categoriesList)
    }
    
    override fun getAllCategories(): Flow<List<Category>> = flow {
        delay(2000) // Simulate delay
        emitAll(categoriesFlow)
    }

    override fun getCategoryById(id: String): Category? {
        return _categoriesList.find { it.id == id }
    }
}
