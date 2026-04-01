package id.sch.smkn1bantul.latihanlks2.repository

import android.content.Context
import id.sch.smkn1bantul.latihanlks2.network.ApiService

class ProductRepository(
    private val api: ApiService,
    context: Context,
) : BaseRepository(context) {
    suspend fun getProduct() = safeApiCall {
        api.getProduct()
    }

    suspend fun deleteProduct(id: String) = safeApiCall {
        api.deleteProduct(id)
    }
}


//    suspend fun createProduct(name: String, categoryid) = safeApiCall {
//        api.createProduct()
//
//    }