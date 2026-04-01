package id.sch.smkn1bantul.latihanlks2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.sch.smkn1bantul.latihanlks2.model.signin.SignInResponse
import id.sch.smkn1bantul.latihanlks2.model.signup.SignUpResponse
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    // Save Token
    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }



    // SignUp
    // Setter
    private  val _signupResponse = MutableLiveData<NetworkResource<SignUpResponse>>()

    // Getter
    val signupResponse: MutableLiveData<NetworkResource<SignUpResponse>> = _signupResponse

    // Function
    fun signup (
        fullName: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) = viewModelScope.launch {
        _signupResponse.value = NetworkResource.Loading
        _signupResponse.value = repository.signup(fullName, email, password, passwordConfirmation)
    }

    // SignIn
    // Setter

    private val _signinResponse = MutableLiveData<NetworkResource<SignInResponse>>()

    //Getter
    val signinResponse: MutableLiveData<NetworkResource<SignInResponse>> = _signinResponse

    // Function
    fun signin (
        email: String,
        password: String
    ) = viewModelScope.launch {
        _signinResponse.value = NetworkResource.Loading
        _signinResponse.value = repository.signin(email, password)
    }


}