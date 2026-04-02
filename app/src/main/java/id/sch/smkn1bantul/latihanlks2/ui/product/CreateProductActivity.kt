package id.sch.smkn1bantul.latihanlks2.ui.product

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.sch.smkn1bantul.latihanlks2.databinding.ActivityCreateProductBinding
import id.sch.smkn1bantul.latihanlks2.network.NetworkResource
import id.sch.smkn1bantul.latihanlks2.utils.ImagePickerUtils
import id.sch.smkn1bantul.latihanlks2.viewmodel.ProductViewModel
import id.sch.smkn1bantul.latihanlks2.viewmodel.ViewModelFactory
import okhttp3.MultipartBody
import java.io.File

class CreateProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateProductBinding

    private var currentImageUri: Uri? = null

    private var currentImageFile: File? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    currentImageUri = it
                    currentImageFile = ImagePickerUtils.uriToFile(this,it)
                    binding.ivPreview.setImageURI(it)
                }
            }

        }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        isSuccess ->
        if (isSuccess) {
            currentImageUri?.let {
                currentImageFile = ImagePickerUtils.uriToFile(this, it)
                binding.ivPreview.setImageURI(it)
            }
        }
    }


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

        binding.btnGaleri.setOnClickListener {
            ImagePickerUtils.openGallery(this, galleryLauncher)
        }
        binding.btnKamera.setOnClickListener {
            currentImageUri = ImagePickerUtils.openCamera(this, cameraLauncher)
        }


    }

    private fun loadView() {
        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val price = binding.etPrice.text.toString()
            val description =  binding.etDescription.text.toString()
            val categoryId = binding.etCategoryId.text.toString()

            val imageMultipart: MultipartBody.Part? = currentImageFile?.let {
                ImagePickerUtils.fileToMultipart(it, "image")
            }

            if (imageMultipart != null) {
                productViewModel.createProduct(name, categoryId, imageMultipart, price, description)
            }

//         productViewModel.createProduct(name, categoryId, image, price, description)

        }

        productViewModel.createProductResponse.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is NetworkResource.Success -> {
                    val data = response.data
                    println("CREATE PRODUCT ACTIVITY --> SUCCESS -- $data")
                    Toast.makeText(this, "Product Created", Toast.LENGTH_SHORT).show()

                    setResult(RESULT_OK)
                    finish()

                }
                is NetworkResource.Error -> {
                    val message = response.errorBody
                    println("CREATE PRODUCT ACTIVITY --> ERROR -- $message")
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
                is NetworkResource.Loading -> {}
            }
        })
    }
}
