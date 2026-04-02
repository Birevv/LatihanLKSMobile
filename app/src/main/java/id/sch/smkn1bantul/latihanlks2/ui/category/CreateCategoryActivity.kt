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
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityCreateCategoryBinding
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.viewmodel.CategoryViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory

class CreateCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("Create Category")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        categoryViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[CategoryViewModel::class.java]
        loadView()
    }

    private fun loadView() {
        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            categoryViewModel.addCategory(name)
        }
        categoryViewModel.addCategoryResponse.observe(this, Observer {
            val response = it ?: return@Observer
            when(response) {
                is NetworkResource.Success -> {
                    val data = response.data

                    println("CREATE CATEGORY ACTIVITY ---> SUCCESS ---> $data")
                    Toast.makeText(this,"Category Created", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()

                }
                is NetworkResource.Error -> {
                    val message = response.errorBody
                    println("CREATE CATEGORY ACTIVITY ---> FAILED ---> $message")
                    Toast.makeText(this, "Failed to Create Category", Toast.LENGTH_SHORT).show()
                }
                is NetworkResource.Loading -> {

                }
            }


        })
    }
}
