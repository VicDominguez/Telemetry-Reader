package es.upm.etsisi.lectortelemetrias.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.etsisi.lectortelemetrias.R
import es.upm.etsisi.lectortelemetrias.ui.navigation.AppRoutes
import es.upm.etsisi.lectortelemetrias.ui.theme.LectorTelemetriasTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController)
{
    Scaffold(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                modifier = Modifier.fillMaxWidth(0.75f),
                painter = painterResource(id = R.drawable.logo_escuela),
                contentDescription = "logo")
            Text(text = stringResource(R.string.welcome_text),
                style = MaterialTheme.typography.titleMedium
            )
            // TODO Apartados 6 y 7 - Botón que aparezca y que se navegue a la ventana de archivos

        }
    }
}

@Preview
@Composable
fun MenuScreenPreview()
{
    val navController = rememberNavController()
    LectorTelemetriasTheme()
    {
        MenuScreen(navController = navController)
    }
}