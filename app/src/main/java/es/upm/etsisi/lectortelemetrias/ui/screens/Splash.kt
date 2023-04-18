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

        navController.navigate(AppRoutes.Menu.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically)
    ) {
        Image(painter = painterResource(id = R.drawable.logo_defecto),
            contentDescription = "Logo aplicación",
            modifier = Modifier.fillMaxSize(0.5F))
        Text(text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium.copy(
                shadow = Shadow(
                    color = MaterialTheme.colorScheme.onBackground,
                    offset = Offset(4f, 4f),
                    blurRadius = 8f
                )
            )
        )
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