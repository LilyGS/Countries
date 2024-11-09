package mx.delasalle.countries.datasources

import mx.delasalle.countries.model.Country
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface CountryApiService {
    @GET("api/Country")
   //@GET("https://restcountries.com/v3.1/all")
    suspend fun getAllCountries():List<Country>

    @POST("api/Country")
    suspend fun addCountry(@Body country: Country): Response<Country>

}