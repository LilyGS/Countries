package mx.delasalle.countries

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.delasalle.countries.ui.screens.CountryListScreen
import mx.delasalle.countries.ui.screens.CountryAddScreen


@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list_screen"){
        composable("list_screen"){
            CountryListScreen(navController)
        }

        composable("add_screen"){
            CountryAddScreen(navController)
        }
    }
}