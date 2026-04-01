package id.sch.smkn1bantul.latihanlks2.model.signup


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message")
    val message: String?
)