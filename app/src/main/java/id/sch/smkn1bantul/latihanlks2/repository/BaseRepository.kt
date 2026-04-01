package id.sch.smkn1bantul.latihanlks2.repository

import android.content.Context
import id.sch.smkn1bantul.latihanlks2.local.UserPrefs
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException

abstract class BaseRepository(context: Context) {
    private val prefs = UserPrefs(context)

    suspend fun saveAuthToken(token: String) {
        prefs.saveAuthCredentials(token)
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> NetworkResource.Error(
                        false, throwable.code(), throwable.response()?.errorBody(), null
                    )
                    is ConnectException -> NetworkResource.Error(true, null, null, null)
                    else -> NetworkResource.Error(false, null, null, throwable.message)
                }
            }
        }
    }


}