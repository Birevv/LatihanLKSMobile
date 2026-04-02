package id.sch.smkn1bantul.latihanlks2.ui.product

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.sch.smkn1bantul.latihanapi.adapter.ProductAdapter
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityProductBinding
import id.sch.smkn1bantul.latihanlks2.local.UserPrefs
import id.sch.smkn1bantul.latihanlks2.model.products.Product
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.viewmodel.ProductViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class ProductActivity : AppCompatActivity(), ProductAdapter.ProductClickListener {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var userPrefs: UserPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("List Produk")

        productViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ProductViewModel::class.java]
        loadView()
        userPrefs = UserPrefs(this)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, CreateProductActivity::class.java)
            startActivityForResult(intent, 100)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            productViewModel.getProduct()
        }
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
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra("PRODUCT", item)
        startActivity(intent)
    }


    override fun onProductDeleted(item: Product) {
        productViewModel.deleteProduct(item.id.toString())

        productViewModel.deleteProductResponse.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is NetworkResource.Success -> {
//                    binding.pbLoading.isVisible = false
//                    val data = response.data.message

                    // Message Sucssess
                    Toast.makeText(this, "Product Deleted", Toast.LENGTH_SHORT).show()

                    // Refresh Product
                    productViewModel.getProduct()

                }

                is NetworkResource.Error -> {
                    Toast.makeText(this, "Product Not Deleted", Toast.LENGTH_SHORT).show()
                }

                is NetworkResource.Loading -> {}
            }


        })
    }
}