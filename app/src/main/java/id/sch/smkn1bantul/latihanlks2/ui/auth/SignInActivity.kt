package id.sch.smkn1bantul.latihanlks2.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivitySignInBinding
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.ui.product.ProductActivity
import id.sch.smkn1bantul.latihanlks2.viewmodel.AuthViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class SignInActivity : AppCompatActivity() {

    private lateinit var  binding: ActivitySignInBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init ViewModel
        authViewModel = ViewModelProvider(this, ViewModelFactory(this)).get(AuthViewModel::class.java)

        // Route Sign Up
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Sign In
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            authViewModel.signin(email, password)

        }

        // Observe Sign In
        authViewModel.signinResponse.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {

                // Show SignIn Sucsess
                is NetworkResource.Success -> {
                    binding.pbLoading.isVisible = false

                    // Print Token
                    val data = response.data.token
                    println("SIGN UP ACTIVITY ---> Sign In Sukses : $data")

                    // Notification
                    Toast.makeText(this, "Sign In Berhasil", Toast.LENGTH_SHORT).show()

                    // Save Token
                    authViewModel.saveAuthToken(data.toString())

                    // Redirect
                    startActivity(Intent(this, ProductActivity::class.java))


                }

                // Show SignIn Error
                is NetworkResource.Error -> {
                    binding.pbLoading.isVisible = false

                    // Show Data Error
                    val data = response.errorBody

                    // Show Error Message
                    println("SIGN IN ACTIVITY ---> Sign In Gagal : $data")
                    Toast.makeText(this,"Sign In Error.", Toast.LENGTH_SHORT).show()
                }

                // Loading
                is NetworkResource.Loading -> {
                    binding.pbLoading.isVisible = true
                }
            }
        })
    }

}