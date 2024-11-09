package mx.delasalle.countries.repositories

import mx.delasalle.countries.datasources.RetrofitInstance
import mx.delasalle.countries.model.Country
import retrofit2.Response
import retrofit2.http.Body

class CountryRepository {
    private val api = RetrofitInstance.api

    suspend fun getCountries(): List<Country> {
        val result = api.getAllCountries()
        return result
    }

    // Función para agregar un nuevo país
    suspend fun addCountry(@Body country: Country): Response<Country> {
        val result = api.addCountry(country)
        return result
    }

}