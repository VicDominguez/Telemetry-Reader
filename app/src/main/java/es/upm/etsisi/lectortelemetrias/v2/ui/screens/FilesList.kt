package es.upm.etsisi.lectortelemetrias.v2.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upm.etsisi.lectortelemetrias.v2.R
import es.upm.etsisi.lectortelemetrias.v2.ui.navigation.AppRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesList(navController: NavController)
{
    // Listado de csv disponibles
    val csvFiles = LocalContext.current.assets.list("csv")

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.files)) },
                navigationIcon =
                {
                    IconButton(onClick = { navController.popBackStack() })
                    {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                }
            )
        }
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it), //padding para que no se superponga la cabecera
        )
        {
            // Como no sabemos los elementos que son, hacemos que solo se carguen los visibles
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), //padding para que no se superponga la cabecera,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            )
            {
                // Si el listado no es nulo, lo pintamos
                csvFiles?.let { files ->
                    items(files)
                    {
                        // Para cada uno hacemos que se muestre el nombre del archivo y que si se le
                        // pulsa se navegue a la pantalla en cuestión
                        Card {
                            Text(
                                text = it.toString(),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        navController.navigate(AppRoutes.Chart.route + "/${it}")
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}