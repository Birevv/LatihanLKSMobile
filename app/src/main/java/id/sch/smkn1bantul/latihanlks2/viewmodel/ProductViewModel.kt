package id.sch.smkn1bantul.latihanlks2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.sch.smkn1bantul.latihanlks2.model.BaseResponse
import id.sch.smkn1bantul.latihanlks2.model.products.ProductResponse
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.repository.ProductRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProductViewModel(
    private val repository: ProductRepository

) : ViewModel() {

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

    private val _deleteProductResponse = MutableLiveData<NetworkResource<BaseResponse>>()

    val deleteProductResponse: LiveData<NetworkResource<BaseResponse>> = _deleteProductResponse

    fun deleteProduct(id: String) = viewModelScope.launch {
        _deleteProductResponse.value = NetworkResource.Loading
        _deleteProductResponse.value = repository.deleteProduct(id)
    }

    private val _createProductResponse = MutableLiveData<NetworkResource<BaseResponse>>()

    val createProductResponse: LiveData<NetworkResource<BaseResponse>> = _createProductResponse


    fun createProduct(
        name: String,
        categoryId: String,
        image: MultipartBody.Part,
        price: String,
        description: String
    ) = viewModelScope.launch {
        _createProductResponse.value = NetworkResource.Loading
        _createProductResponse.value = repository.createProduct(
            name,
            categoryId,
            image,
            price,
            description

        )
    }
}
