package id.sch.smkn1bantul.latihanlks2.model.products


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("data")
    val `data`: List<Product?>?
)