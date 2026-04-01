package id.sch.smkn1bantul.latihanlks2.model

import com.google.gson.annotations.SerializedName

//data class BaseResponse<T>(
//    val data: T,
//    val message: String,
//    val status: String
//)

data class BaseResponse(
    @SerializedName("message")
    val message: String?
)