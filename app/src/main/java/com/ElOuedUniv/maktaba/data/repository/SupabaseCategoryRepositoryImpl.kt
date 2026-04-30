package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupabaseCategoryRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = flow {
        try {
            // جلب البيانات من جدول categories في Supabase
            val categories = supabaseClient.postgrest["categories"]
                .select()
                .decodeList<Category>()
            emit(categories)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override fun getCategoryById(id: String): Category? {
        // يمكن تنفيذها لاحقاً إذا احتجت تفاصيل تصنيف معين
        return null
    }
}
