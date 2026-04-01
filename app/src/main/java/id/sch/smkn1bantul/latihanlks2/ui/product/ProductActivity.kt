package id.sch.smkn1bantul.latihanlks2.ui.product

import android.content.Intent
import android.content.res.ObbScanner
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.sch.smkn1bantul.latihanapi.adapter.ProductAdapter
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityProductBinding
import id.sch.smkn1bantul.latihanlks2.local.UserPrefs
import id.sch.smkn1bantul.latihanlks2.model.products.Product
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.ui.auth.SignInActivity
import id.sch.smkn1bantul.latihanlks2.viewmodel.ProductViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity(), ProductAdapter.ProductClickListener {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productViewModel: ProductViewModel
    private  lateinit var userPrefs: UserPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar.appBar)
        supportActionBar ?.setTitle("List Produk")

        productViewModel = ViewModelProvider(this, ViewModelFactory(this))[ProductViewModel::class.java]
        loadView()


        userPrefs = UserPrefs(this)

        // Logout Button
//        binding.btnLogout.setOnClickListener {
//            lifecycleScope.launch {
//                userPrefs.clear()
//
//                 val intent = Intent(this@ProductActivity, SignInActivity::class.java)
//                 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                 startActivity(intent)
//
//            }
//        }
    }

    private fun loadView() {
        // Get Product

        productViewModel.getProduct()

        // Observe Product

        productViewModel.ListProduct.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is NetworkResource.Success -> {
                    binding.pbLoading.isVisible = false
                    val data = response.data.data

                    val adapter = ProductAdapter(this)
                    val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    binding.rvMain.layoutManager = layoutManager
                    binding.rvMain.adapter = adapter
                    adapter.submitList(data)

                }
                is NetworkResource.Error -> {
                    binding.pbLoading.isVisible = false
                }
                is NetworkResource.Loading -> {
                    binding.pbLoading.isVisible = true
                }
            }
        })

    }

    override fun onProductClicked(item: Product) {
        // Handle Product Click
    }
}