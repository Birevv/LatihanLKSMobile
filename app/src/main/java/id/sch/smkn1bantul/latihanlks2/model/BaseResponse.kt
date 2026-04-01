package id.sch.smkn1bantul.latihanlks2.model

data class BaseResponse<T>(
    val data: T,
    val message: String,
    val status: String
)