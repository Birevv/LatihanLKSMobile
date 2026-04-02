package id.sch.smkn1bantul.latihanlks2.repository

import android.content.Context
import id.sch.smkn1bantul.latihanlks2.network.ApiService

class CategoryRepository(
    private val api: ApiService,
    context: Context,
) : BaseRepository(context) {
    suspend fun getCategory() = safeApiCall {
        api.getCategory()
    }

    suspend fun addCategory(name: String) = safeApiCall {
        api.createCategory(name)
    }

    suspend fun deleteCategory(id: String) = safeApiCall {
        api.deleteCategory(id)
    }
}
