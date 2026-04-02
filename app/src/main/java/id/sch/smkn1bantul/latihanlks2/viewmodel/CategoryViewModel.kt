package id.sch.smkn1bantul.latihanlks2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.sch.smkn1bantul.latihanlks2.model.BaseResponse
import id.sch.smkn1bantul.latihanlks2.model.category.CategoryResponse
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    // List Category

    // Setter
    private val _ListCategory = MutableLiveData<NetworkResource<CategoryResponse>>()

    // Getter
    val ListCategory: LiveData<NetworkResource<CategoryResponse>> = _ListCategory

    // Function
    fun getCategory() = viewModelScope.launch {
        _ListCategory.value = NetworkResource.Loading
        _ListCategory.value = repository.getCategory()

    }

    private val _addCategoryResponse = MutableLiveData<NetworkResource<BaseResponse>>()

    val addCategoryResponse: LiveData<NetworkResource<BaseResponse>> = _addCategoryResponse

    fun addCategory(name: String) = viewModelScope.launch {
        _addCategoryResponse.value = NetworkResource.Loading
        _addCategoryResponse.value = repository.addCategory(name)

    }

}