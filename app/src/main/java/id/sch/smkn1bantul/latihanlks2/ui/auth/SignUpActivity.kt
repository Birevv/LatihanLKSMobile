package id.sch.smkn1bantul.latihanlks2.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivitySignUpBinding
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.viewmodel.AuthViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // init ViewModel
        authViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(AuthViewModel::class.java)

        // Route Back To Sign In
        binding.tvSignin.setOnClickListener {
            finish()
        }

        // Sign Up
        binding.btnSignup.setOnClickListener {
            val fullName = binding.etFullname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmationPassword = binding.etPasswordConfirmation.text.toString()
            authViewModel.signup(fullName, email, password, confirmationPassword)
        }

        // Observe Sign Up
        authViewModel.signupResponse.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {

                // Show SignUp Sucsess
                is NetworkResource.Success -> {
                    binding.pbLoading.isVisible = false

                    val data = response.data
                    println("SIGN UP ACTIVITY ---> Register Success : $data")
                    Toast.makeText(this, "Sign Up Berhasil", Toast.LENGTH_SHORT).show()


                    // Redirect Signin
                    finish()
                }

                // Show Error SignUp
                is NetworkResource.Error -> {
                    binding.pbLoading.isVisible = false

                    // Show Data Error
                    val data = response.errorBody
                    println("SIGN UP ACTIVITY ---> Register Gagal : $data")

                    // Show Error Message
                    Toast.makeText(this, "Register Error", Toast.LENGTH_SHORT).show()

                }

                // Loading
                is NetworkResource.Loading -> {
                    binding.pbLoading.isVisible = true
                }

            }


        })


    }
}