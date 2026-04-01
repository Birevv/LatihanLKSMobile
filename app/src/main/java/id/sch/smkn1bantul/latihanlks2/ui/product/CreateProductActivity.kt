package id.sch.smkn1bantul.latihanlks2.ui.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityCreateProductBinding
import id.sch.smkn1bantul.latihanlks2.viewmodel.ProductViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class CreateProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateProductBinding

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("Create Produk")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        productViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ProductViewModel::class.java]
        loadView()


    }

    private fun loadView() {
        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString()
            val description =  binding.etDescription.text.toString()

//            productViewModel.createProduct(name, price, description)

        }
    }
}
