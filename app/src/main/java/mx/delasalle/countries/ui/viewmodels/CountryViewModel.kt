package mx.delasalle.countries.ui.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.delasalle.countries.datasources.RetrofitInstance
import mx.delasalle.countries.model.Country
import mx.delasalle.countries.repositories.CountryRepository

sealed class CountryUiState {
    object Loading : CountryUiState()
    data class Success(val countries: List<Country>) : CountryUiState()
    data class Error(val message: String) : CountryUiState()
}

sealed class CountryAddUiState {
    object Idle : CountryAddUiState()
    object Loading : CountryAddUiState()
    object Success : CountryAddUiState()
    data class Error(val message: String) : CountryAddUiState()
}

class CountryViewModel(
    private val repository: CountryRepository = CountryRepository()
) : ViewModel() {

    private val _addUiState = MutableStateFlow<CountryAddUiState>(CountryAddUiState.Idle)
    val addUiState: StateFlow<CountryAddUiState> = _addUiState

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries

    private val _uiState = MutableStateFlow<CountryUiState>(CountryUiState.Loading)
    val uiState: StateFlow<CountryUiState> = _uiState


    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            try {
                _uiState.value = CountryUiState.Loading
                val countries = repository.getCountries()
                _uiState.value = CountryUiState.Success(countries)
            } catch (e: Exception) {
                _uiState.value = CountryUiState.Error(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun reloadCountries(){
        fetchCountries()
    }

    //  para agregar un nuevo país
    private fun addCountry(country: Country) {
        viewModelScope.launch {
            _addUiState.value = CountryAddUiState.Loading
            try {
                repository.addCountry(country)
                fetchCountries()
                _addUiState.value = CountryAddUiState.Success

            } catch (e: Exception) {
                _addUiState.value = CountryAddUiState.Error(e.localizedMessage ?: "Error al guardar")
            }
        }
    }

    fun onAddCountry(country: Country) {
        addCountry(country)
    }


}



