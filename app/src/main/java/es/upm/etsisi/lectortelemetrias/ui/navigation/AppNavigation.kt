package es.upm.etsisi.lectortelemetrias.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.upm.etsisi.lectortelemetrias.ui.screens.FilesList
import es.upm.etsisi.lectortelemetrias.ui.screens.MenuScreen
import es.upm.etsisi.lectortelemetrias.ui.screens.charts.ChartScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.Menu.route)
    {
        composable(route = AppRoutes.Menu.route)
        {
            MenuScreen(navController)
        }
        composable(route = AppRoutes.FilesList.route)
        {
            FilesList(navController)
        }
        composable(
            route = AppRoutes.Chart.route + "/{filename}",
            arguments = listOf(navArgument(name = "filename") {type = NavType.StringType}))
        {
            it.arguments?.getString("filename")?.let { filename -> ChartScreen(navController, filename) }
        }
    }
}