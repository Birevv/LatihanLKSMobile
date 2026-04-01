package id.sch.smkn1bantul.latihanlks2.model.signin


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("token")
    val token: String?
)