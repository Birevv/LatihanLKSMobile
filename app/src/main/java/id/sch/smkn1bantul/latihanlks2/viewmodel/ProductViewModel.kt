package id.sch.smkn1bantul.latihanlks2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.sch.smkn1bantul.latihanlks2.model.products.ProductResponse
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository

): ViewModel() {

    // List Product

    // Setter
    private val _ListProduct = MutableLiveData<NetworkResource<ProductResponse>>()

    // Getter
    val ListProduct: LiveData<NetworkResource<ProductResponse>> = _ListProduct

    // Function
    fun getProduct() = viewModelScope.launch {
        _ListProduct.value = NetworkResource.Loading
        _ListProduct.value = repository.getProduct()

    }
}