package id.sch.smkn1bantul.latihanlks2.ui.category

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.adapter.CategoryAdapter
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityCategoryBinding
import id.sch.smkn1bantul.latihanlks2.model.products.Category
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.viewmodel.CategoryViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class CategoryActivity : AppCompatActivity(), CategoryAdapter.CategoryClickListener {

    private lateinit var binding: ActivityCategoryBinding

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("List Kategori")

        categoryViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[CategoryViewModel::class.java]
        loadView()


    }

    private fun loadView() {

        // Get Category
        categoryViewModel.getCategory()

        // Observe Category

        categoryViewModel.ListCategory.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is NetworkResource.Success -> {
                    binding.pbLoading.isVisible = false
                    val data = response.data.data

                    val adapter = CategoryAdapter(this)
                    binding.rvCategory.adapter = adapter
                    val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    binding.rvCategory.layoutManager = layoutManager
                    binding.rvCategory.adapter = adapter
                    adapter.submitList(data)
                }
                is NetworkResource.Loading -> {
                    binding.pbLoading.isVisible = true
                }
                is NetworkResource.Error -> {
                    binding.pbLoading.isVisible = false
                }
            }
        })
    }

    override fun onCategoryClicked(item: Category) {
        TODO("Not yet implemented")
    }

    override fun onCategoryDeleted(item: Category) {
        TODO("Not yet implemented")
    }

    override fun onCategoryEdited(item: Category) {
        TODO("Not yet implemented")
    }
}