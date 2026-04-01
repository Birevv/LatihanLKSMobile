package id.sch.smkn1bantul.latihanlks2.network

import okhttp3.ResponseBody

sealed class NetworkResource<out T> {

    data class Success<out T>(val data: T) : NetworkResource<T>()

    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val extra: String?,
    ) : NetworkResource<Nothing>()

    object Loading : NetworkResource<Nothing>()
}