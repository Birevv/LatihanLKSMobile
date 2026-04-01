package id.sch.smkn1bantul.latihanlks2.model.products


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?
)