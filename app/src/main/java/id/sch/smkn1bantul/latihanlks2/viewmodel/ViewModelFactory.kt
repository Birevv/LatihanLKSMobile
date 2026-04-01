package id.sch.smkn1bantul.latihanlks2.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.model.products.Product
import id.sch.smkn1bantul.latihanlks2.network.Api
import id.sch.smkn1bantul.latihanlks2.network.ApiService
import id.sch.smkn1bantul.latihanlks2.repository.AuthRepository
import id.sch.smkn1bantul.latihanlks2.repository.ProductRepository

class ViewModelFactory(
    val context: Context,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(
                AuthRepository(Api.build(context), context)
            ) as T

            modelClass.isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(
                ProductRepository(Api.build(context), context)
            ) as T


            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}