package id.sch.smkn1bantul.latihanlks2.ui.product

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.R
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityEditProductBinding
import id.sch.smkn1bantul.latihanlks2.model.products.Product
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.utils.ImagePickerUtils
import id.sch.smkn1bantul.latihanlks2.utils.loadImageWithUri
import id.sch.smkn1bantul.latihanlks2.viewmodel.ProductViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory
import okhttp3.MultipartBody
import java.io.File

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private var currentImageFile: File? = null
    private lateinit var currentImageUri: Uri
    private lateinit var productViewModel: ProductViewModel


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    currentImageUri = it
                    currentImageFile = ImagePickerUtils.uriToFile(this, it)
                    binding.ivPreview.setImageURI(it)
                }
            }
        }
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                currentImageUri?.let {
                    currentImageFile = ImagePickerUtils.uriToFile(this, it)
                    binding.ivPreview.setImageURI(it)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(
            binding.root
        )
        setSupportActionBar(binding.appBarInclude.appBar)
        supportActionBar?.setTitle("EditProudk")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        productViewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ProductViewModel::class.java]
        loadView()

        binding.btnGaleri.setOnClickListener {
            ImagePickerUtils.openGallery(this, galleryLauncher)
        }
        binding.btnKamera.setOnClickListener {
            currentImageUri = ImagePickerUtils.openCamera(this, cameraLauncher)
        }


        //Load product
        val product = intent.getParcelableExtra<Product>("PRODUCT")
        binding.etName.setText(product?.name)
        binding.etCategoryId.setText(product?.category?.id.toString())
        binding.etPrice.setText(product?.price.toString())
        binding.etDescription.setText(product?.description)
        binding.ivPreview.loadImageWithUri(product?.imageUrl?.toUri(), false)


    }

    private fun loadView() {
        // submit Edit
        binding.btnSubmit.setOnClickListener {
            val product = intent.getParcelableExtra<Product>("PRODUCT")
            val id = product?.id.toString()
            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString()
            val description = binding.etDescription.text.toString()
            val categoryId = binding.etCategoryId.text.toString()

            val imageMultipart: MultipartBody.Part? = currentImageFile?.let {
                ImagePickerUtils.fileToMultipart(it, "image")
            }
            if (imageMultipart != null) {
                productViewModel.editProduct(
                    id,
                    name,
                    categoryId,
                    imageMultipart,
                    price,
                    description
                )
            } else {
                Toast.makeText(this, "Gambar Harus Diganti", Toast.LENGTH_SHORT).show()
            }

        }

        // Observe Edit
        productViewModel.createProductResponse.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is NetworkResource.Success -> {
                    val data = response.data
                    println("EDIT PRODUCT ACTIVITY --> SUCCESS -- $data")
                    Toast.makeText(this, "Product Created", Toast.LENGTH_SHORT).show()

                    setResult(RESULT_OK)
                    finish()

                }

                is NetworkResource.Error -> {
                    val message = response.errorBody
                    println("EDIT PORDUCT ACTIVITY --> ERROR -- $message")
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }

                is NetworkResource.Loading -> {}
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}