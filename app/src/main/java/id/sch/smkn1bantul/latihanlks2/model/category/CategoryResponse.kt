package id.sch.smkn1bantul.latihanlks2.model.category


import com.google.gson.annotations.SerializedName
import id.sch.smkn1bantul.latihanlks2.model.products.Category

data class CategoryResponse(
    @SerializedName("data")
    val `data`: List<Category?>?
)