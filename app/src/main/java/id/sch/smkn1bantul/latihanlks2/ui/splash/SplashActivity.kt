package id.sch.smkn1bantul.latihanlks2.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.local.UserPrefs
import id.sch.smkn1bantul.latihanlks2.ui.auth.SignInActivity
import id.sch.smkn1bantul.latihanlks2.ui.product.ProductActivity
import kotlinx.coroutines.launch
import kotlin.jvm.java

class SplashActivity : AppCompatActivity() {

    private  lateinit var userPrefs: UserPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Init UserPrefs
        userPrefs = UserPrefs(this)

        // Make Variable Token
        var authToken = ""

        // Get Token
        lifecycleScope.launch {
            userPrefs.authToken.collect { token ->
                println("Got Token ---> $token")
                authToken = token ?: ""
            }
        }

        // Running Thread
        val thread = Thread {
            Thread.sleep(1500)

            // Check Token
            if(authToken.isEmpty()) {
                startActivity(Intent(this, SignInActivity::class.java))
            }
            else {
                startActivity(Intent(this, ProductActivity::class.java))
            }
            finish()

        }

        // Start Thread
        thread.start()

    }
}