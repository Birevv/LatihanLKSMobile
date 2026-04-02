package id.sch.smkn1bantul.latihanlks2.ui.category

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityEditCategoryBinding
import id.sch.smkn1bantul.latihanlks2.model.products.Category
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.viewmodel.CategoryViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class EditCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("Edit Category")
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        categoryViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[CategoryViewModel::class.java]
        loadView()

        val category = intent.getParcelableExtra<Category>("CATEGORY")
        binding.etName.setText(category?.name)

    }

    private fun loadView() {
        binding.btnSubmit.setOnClickListener {
            val category = intent.getParcelableExtra<Category>("CATEGORY")
            val id = category?.id.toString()
            val name = binding.etName.text.toString()

            categoryViewModel.editCategory(id, name)
        }
        categoryViewModel.editCategoryResponse.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is NetworkResource.Success -> {
                    val data = response.data
                    println("EDIT CATEGORY ACTIVITY --> SUCCESS -- $data")
                    Toast.makeText(this, "Category Edited", Toast.LENGTH_SHORT).show()

                    setResult(RESULT_OK)
                    finish()
                }
                is NetworkResource.Error -> {
                    val message = response.errorBody
                    println("EDIT CATEGORY ACTIVITY --> ERROR -- $message")
                    Toast.makeText(this,"Error Occured", Toast.LENGTH_SHORT).show()

                }

                is NetworkResource.Loading -> {}

            }
        })
    }


}