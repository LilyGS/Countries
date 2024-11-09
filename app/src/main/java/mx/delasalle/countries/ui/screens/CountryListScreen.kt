package mx.delasalle.countries.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import mx.delasalle.countries.model.Country
import mx.delasalle.countries.ui.Header
import mx.delasalle.countries.ui.viewmodels.CountryUiState
import mx.delasalle.countries.ui.viewmodels.CountryViewModel


@Composable
fun CountryListScreen(navController: NavController) {
    val countryViewModel: CountryViewModel = viewModel()
    val uiState by countryViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        countryViewModel.reloadCountries()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_screen")
            },
                modifier = Modifier.size(80.dp),
                containerColor = Color(0xFF063971),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
        { paddingValues ->
            Header(title = "Inicio")
        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            when (uiState) {
                is CountryUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is CountryUiState.Success -> {
                    val countries = (uiState as CountryUiState.Success).countries
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(countries) { country ->
                            CountryItem(country)
                        }
                    }
                }
                is CountryUiState.Error -> {
                    val message = (uiState as CountryUiState.Error).message
                    Text(
                        text = message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
        }
    }

}

@Composable
fun CountryItem(country: Country) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            country.image?.let { flagUrl ->
                AsyncImage(model = flagUrl,
                    contentDescription = country.name,
                    modifier = Modifier.size(64.dp))
            }
            Column(modifier = Modifier.padding((16.dp))) {
                Text(text = country.name, style = TextStyle(fontSize = 24.sp))
                Spacer(modifier = Modifier.height(4.dp))
                val capital = country.capital
                Text(text = "Capital ${capital}")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun Preview() {

    CountryListScreen(navController = rememberNavController())

}


