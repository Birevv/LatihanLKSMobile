package id.sch.smkn1bantul.latihanlks2.model.signin


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("fullname")
    val fullname: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)