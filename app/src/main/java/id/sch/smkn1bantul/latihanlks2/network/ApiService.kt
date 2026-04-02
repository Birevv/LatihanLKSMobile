package id.sch.smkn1bantul.latihanlks2.network

import android.content.Context
import android.media.Image
import id.sch.smkn1bantul.latihanlks2.local.UserPrefs
import id.sch.smkn1bantul.latihanlks2.model.BaseResponse
import id.sch.smkn1bantul.latihanlks2.model.category.CategoryResponse
import id.sch.smkn1bantul.latihanlks2.model.products.ProductResponse
import id.sch.smkn1bantul.latihanlks2.model.signin.SignInResponse
import id.sch.smkn1bantul.latihanlks2.model.signup.SignUpResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

//    @FormUrlEncoded
//    @POST("register")
//    suspend fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): RegisterResponse

    @FormUrlEncoded
    @POST("user/signup")
    suspend fun signup(
        @Field("fullname") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("user/signin")
    suspend fun signin(
        @Field("email") email: String,
        @Field("password") password: String
    ): SignInResponse

    @GET("product")
    suspend fun getProduct(): ProductResponse

    // Get Kategori
    @GET("category")
    suspend fun getCategory(): CategoryResponse

    // Add Kategori

    @FormUrlEncoded
    @POST("category")
    suspend fun createCategory(
        @Field("name") name: String
    ): BaseResponse

    // Delete Kategori
    @DELETE("category/{id}")
    suspend fun deleteCategory(
        @Path("id") id: String
    ): BaseResponse

    // Edit Kategori
    @FormUrlEncoded
    @PUT("category/{id}")
    suspend fun editCategory(
        @Path("id") id: String,
        @Field("name") name: String
    ): BaseResponse

    @Multipart
    @POST("product")
    suspend fun createProduct(
        @Part("name") name: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("price") price: RequestBody,
        @Part("description") description: RequestBody
    ): BaseResponse

    @Multipart
    @POST("product/{id}")
    suspend fun editProduct(
        @Path("id") id: String,
        @Part("name") name: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("price") price: RequestBody,
        @Part("description") description: RequestBody,
        @Part("_method") method: RequestBody
    ): BaseResponse

    @DELETE("product/{id}")
    suspend fun deleteProduct(
        @Path("id") id: String
    ): BaseResponse


}


object Api {
    //    private const val BASE_URL = "https://dummyjson.com/"
    private const val BASE_URL = "https://indiemart.taufikridho.my.id/api/v1/"

    fun build(context: Context): ApiService {
        val userPrefs = UserPrefs(context)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userPrefs))
            .apply {
                val logging = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(logging)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

class AuthInterceptor(
    private val userPrefs: UserPrefs
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val token = runBlocking {
            userPrefs.authToken.first()
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(request)
    }
}