package es.upm.etsisi.telemetryreader.ui.navigation

/**
 * App available routes to show to the user
 * @see AppNavigation
 */
enum class AppRoutes(val route: String) {
    Splash("splash"),
    Menu("menu"),
    FilesList("files_list"),
    Chart("chart")
}