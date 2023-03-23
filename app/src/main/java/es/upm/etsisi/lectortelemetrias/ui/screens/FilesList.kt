package es.upm.etsisi.lectortelemetrias.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upm.etsisi.lectortelemetrias.ui.navigation.AppRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesList(navController: NavController)
{
    val csvFiles = LocalContext.current.assets.list("csv")
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top))
        {
            Text("Archivos disponibles para visualizar")
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top))
            {
                csvFiles?.let { files ->
                    items(files)
                    {
                        Row(modifier = Modifier
                            .clickable { navController.navigate(AppRoutes.Chart.route + "/${it}")})
                        {
                            Text(it.toString())
                        }
                    }
                }
            }
        }

    }
}