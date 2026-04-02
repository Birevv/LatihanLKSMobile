package id.sch.smkn1bantul.latihanlks2.repository

import android.content.Context
import id.sch.smkn1bantul.latihanlks2.network.ApiService
import id.sch.smkn1bantul.latihanlks2.utils.toRequestBody
import okhttp3.MultipartBody

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

    suspend fun createProduct(
        name: String,
        categoryId: String,
        image: MultipartBody.Part,
        price: String,
        description: String
    ) = safeApiCall {
        api.createProduct(
            name.toRequestBody(),
            categoryId.toRequestBody(),
            image,
            price.toRequestBody(),
            description.toRequestBody()

        )

    }
    suspend fun editProduct(
        id: String,
        name: String,
        categoryId: String,
        image: MultipartBody.Part,
        price: String,
        description: String
    ) = safeApiCall {
        api.editProduct(
            id,
            name.toRequestBody(),
            categoryId.toRequestBody(),
            image,
            price.toRequestBody(),
            description.toRequestBody(),
            "PUT".toRequestBody()

        )

    }
}


//    suspend fun createProduct(name: String, categoryid) = safeApiCall {
//        api.createProduct()
//
//    }