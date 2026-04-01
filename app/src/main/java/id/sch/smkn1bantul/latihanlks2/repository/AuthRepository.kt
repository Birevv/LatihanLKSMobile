package id.sch.smkn1bantul.latihanlks2.repository

import android.content.Context
import id.sch.smkn1bantul.latihanlks2.network.ApiService

class AuthRepository(
    private val api: ApiService,
    context: Context
) : BaseRepository(context) {

    suspend fun signup(
        fullName: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) = safeApiCall {
        api.signup(fullName, email, password, passwordConfirmation)
    }

    suspend fun signin (
        email: String,
        password: String
    ) = safeApiCall {
        api.signin(email, password)
    }
}