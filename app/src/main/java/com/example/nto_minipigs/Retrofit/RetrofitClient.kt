import com.example.nto_minipigs.Retrofit.ApiService
import com.example.nto_minipigs.core.Constants.SERVER_ADDRESS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    val baseUrl = SERVER_ADDRESS

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService : ApiService = getInstance().create(ApiService::class.java)
}