package es.upm.etsisi.telemetryreader.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.upm.etsisi.telemetryreader.ui.screens.FilesList
import es.upm.etsisi.telemetryreader.ui.screens.MenuScreen
import es.upm.etsisi.telemetryreader.ui.screens.SplashScreen
import es.upm.etsisi.telemetryreader.ui.screens.charts.ChartScreen

/**
 * Navigation graph implementation
 * @see AppRoutes
 */
@Composable
fun AppNavigation()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.Splash.route)
    {
        composable(route = AppRoutes.Splash.route)
        {
            SplashScreen(navController = navController)
        }
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
            arguments = listOf(navArgument(name = "filename") {type = NavType.StringType})
        )
        {
            // If filename was received, move to chart screen with that
            it.arguments?.getString("filename")?.let { filename ->
                ChartScreen(navController = navController, filename = filename)
            }
        }
    }
}