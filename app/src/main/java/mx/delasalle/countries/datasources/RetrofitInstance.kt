package mx.delasalle.countries.datasources

import okhttp3.OkHttpClient

import retrofit2.Retrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //private const val BASE_URL = "https://restcountries.com/"
    private const val BASE_URL = "http://52.251.64.79:8080/"

    private val gson: Gson = GsonBuilder().setLenient().create()
    private val client: OkHttpClient = OkHttpClient.Builder().build()

    val api: CountryApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CountryApiService::class.java)
    }

}


