package es.upm.etsisi.telemetryreader.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.etsisi.telemetryreader.R
import es.upm.etsisi.telemetryreader.ui.navigation.AppRoutes
import es.upm.etsisi.telemetryreader.ui.theme.TelemetryReaderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesList(navController: NavController) {
    // List local csv
    val csvFiles = LocalContext.current.assets.list("csv")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.files)) },
                navigationIcon = {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
        {
            // Plot only visible data
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            )
            {
                csvFiles?.let { files ->
                    items(files)
                    {
                        // For each of them, plot filename and if user clicks on it, navigate to corresponding screen
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate(AppRoutes.Chart.route + "/${it}") },
                        ) {
                            Text(
                                text = it.toString(),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FilesListPreview() {
    val navController = rememberNavController()
    TelemetryReaderTheme()
    {
        FilesList(navController = navController)
    }
}