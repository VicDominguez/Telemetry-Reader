package es.upm.etsisi.lectortelemetrias.v2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.upm.etsisi.lectortelemetrias.v2.ui.screens.FilesList
import es.upm.etsisi.lectortelemetrias.v2.ui.screens.MenuScreen
import es.upm.etsisi.lectortelemetrias.v2.ui.screens.charts.ChartScreen

/**
 * Implementación de las rutas del sistema
 * @see AppRoutes
 */
@Composable
fun AppNavigation()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.Menu.route)
    {
        composable(route = AppRoutes.Menu.route)
        {
            MenuScreen(navController = navController)
        }
        composable(route = AppRoutes.FilesList.route)
        {
            FilesList(navController = navController)
        }
        composable(
            route = AppRoutes.Chart.route + "/{filename}",
            arguments = listOf(navArgument(name = "filename") {type = NavType.StringType}))
        {
            //si efectivamente tenemos el parámetro filename, abrimos la ventana de gráficas
            it.arguments?.getString("filename")?.let { filename ->
                ChartScreen(navController = navController, filename = filename)
            }
        }
    }
}