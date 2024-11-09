package mx.delasalle.countries.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.TextStyle
import mx.delasalle.countries.model.Country
import mx.delasalle.countries.ui.Header
import mx.delasalle.countries.ui.viewmodels.CountryAddUiState
import mx.delasalle.countries.ui.viewmodels.CountryViewModel


@Composable
fun CountryAddScreen(navController: NavController) {
    val countryViewModel: CountryViewModel = viewModel()
    val addUiState by countryViewModel.addUiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var capital by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("list_screen")
                },
                modifier = Modifier.size(80.dp),
                containerColor = Color(0xFF063971),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Back")
            }
        }
    ) { paddingValues ->
        Header(title = "Captura")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
               /* Text(
                    text = "Ingresa los datos solicitados.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF063971),
                    modifier = Modifier
                        //.fillMaxWidth() no va esta instrucción porque después no centra el texto
                        .padding(18.dp),
                )*/

                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                   // label = { Text("Nombre País") },
                    label = {
                        if (name.isEmpty()) {
                            Text("Nombre País") // Muestra el label solo si name está vacío
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)

                )
                TextField(
                    value = capital,
                    onValueChange = {
                        capital = it
                    },
                    label = {
                        if (capital.isEmpty()) {
                            Text("Capital País") // Muestra el label solo si capital está vacío
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                )
                TextField(
                    value = imageUrl,
                    onValueChange = {
                        imageUrl = it
                    },
                    label = {
                        if (imageUrl.isEmpty()) {
                            Text("Url Bandera") // Muestra el label solo si imageUrl está vacío
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                )
                Button(
                    onClick = {
                        if (name.isBlank() || capital.isBlank() || imageUrl.isBlank()) {
                            errorMessage = "Todos los campos son obligatorios."
                        } else {
                            val newCountry =
                                Country(name = name, capital = capital, image = imageUrl)
                            countryViewModel.onAddCountry(newCountry)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF063971),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .height(60.dp)

                ) {
                    Text("AGREGAR", style = TextStyle(fontSize = 20.sp))
                }

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }


                when (addUiState) {
                    is CountryAddUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    is CountryAddUiState.Success -> {
                        Text("País agregado correctamente", color = Color(0xFF4C9444))
                        LaunchedEffect(Unit) {
                            navController.popBackStack()
                        }
                    }

                    is CountryAddUiState.Error -> {
                        val message = (addUiState as CountryAddUiState.Error).message
                        Text(text = message, color = Color.Red)
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountryAddScreen() {
    CountryAddScreen(navController = rememberNavController())
}