package id.sch.smkn1bantul.latihanlks2.ui.product

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityDetailedProductBinding
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityProductBinding
import id.sch.smkn1bantul.latihanlks2.model.products.Product
import id.sch.smkn1bantul.latihanlks2.utils.loadImageWithUri
import id.sch.smkn1bantul.latihanlks2.viewmodel.ProductViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class DetailedProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<Product>("PRODUCT")

        binding.tvName.text = product?.name
        binding.tvPrice.text = product?.price.toString()
        binding.tvCategory.text = product?.category?.name
        binding.tvDescription.text = product?.description
        binding.ivProduct.loadImageWithUri(product?.imageUrl?.toUri(), true)


        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("Detail Produk")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}