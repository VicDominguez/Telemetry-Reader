package es.upm.etsisi.lectortelemetrias.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.etsisi.lectortelemetrias.ui.navigation.AppRoutes
import es.upm.etsisi.lectortelemetrias.R
import es.upm.etsisi.lectortelemetrias.ui.theme.LectorTelemetriasTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController)
{
    LaunchedEffect(true)
    {
        delay(1000)

        navController.popBackStack()

        // TODO Apartado 5.3 - navegación al menú


    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically)
    ) {
        // TODO Apartado 3 - Icono de la aplicación


        // TODO Apartado 4 - Texto de bienvenida


    }
}

@Preview
@Composable
fun SplashScreenPreview()
{
    val navController = rememberNavController()
    LectorTelemetriasTheme()
    {
        SplashScreen(navController = navController)
    }
}