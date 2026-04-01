package id.sch.smkn1bantul.latihanlks2.model.CreateProduct


import com.google.gson.annotations.SerializedName

data class CreateProductResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?
)