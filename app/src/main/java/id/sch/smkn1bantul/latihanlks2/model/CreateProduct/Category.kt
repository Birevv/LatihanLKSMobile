package id.sch.smkn1bantul.latihanlks2.model.CreateProduct


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)