package es.upm.etsisi.telemetryreader.ui.screens.charts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import es.upm.etsisi.telemetryreader.R
import es.upm.etsisi.telemetryreader.csv.Measure
import es.upm.etsisi.telemetryreader.ui.theme.TelemetryReaderTheme
import es.upm.etsisi.telemetryreader.ui.utils.Entry
import es.upm.etsisi.telemetryreader.ui.utils.rememberMarker
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(navController: NavController, filename: String) {
    val viewModel: ChartViewModel = viewModel(
        factory = ChartViewModel.Factory(
            assetManager = LocalContext.current.assets,
            filename = filename
        )
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = filename) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                DisplayCategoryMenu(
                    label = stringResource(id = R.string.category),
                    options = Measure.values(),
                    selected = state,
                    onChange = { measure ->
                        viewModel.onCategoryChange(measure)
                    }
                )
                DisplayChart(state = state, producer = viewModel.producer)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayCategoryMenu(
    label: String,
    options: Array<Measure>,
    selected: Measure,
    onChange: (Measure) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    //Menu entry point
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            // Ready only field
            readOnly = true,
            value = stringResource(id = selected.label()),
            // Ready only field
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        // Menu options
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        )
        {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = selectionOption.label())) },
                    onClick = {
                        expanded = false
                        onChange(selectionOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun DisplayChart(
    state: Measure,
    producer: ChartEntryModelProducer
) {
    val chartStyle = m3ChartStyle()

    // It will be a constant between recompositions
    val dtf = remember { DateTimeFormatter.ofPattern("HH:mm:ss") }

    val valueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { index, chartValues ->
        // With index we can select proper entry and with timestamp format can be obtained
        (chartValues.chartEntryModel.entries.first().getOrNull(index.toInt()) as? Entry)
            ?.timestamp
            ?.run {
                dtf.format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault()))
            }
            .orEmpty()
    }

    ProvideChartStyle(chartStyle)
    {
        Chart(
            chart = lineChart(
                spacing = 48.dp,
                // Onverride only if a range is available
                axisValuesOverrider = state.range?.let {
                    AxisValuesOverrider.fixed(null, null, it.lower, it.upper)
                },
            ),
            model = producer.getModel(),
            modifier = Modifier.fillMaxSize(),
            // Left axis with category as title
            startAxis = startAxis(
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(id = state.label())
            ),
            bottomAxis = bottomAxis(
                label = axisLabelComponent(textSize = 10f.sp),
                // Formatter to set timestamp for each value
                valueFormatter = valueFormatter,
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(id = R.string.timestamp)
            ),
            // Set a mark when user touch it
            marker = rememberMarker()
        )
    }
}

fun Measure.label(): Int {
    return when (this) {
        Measure.Humidity -> R.string.humidity
        Measure.Temperature -> R.string.temperature
        Measure.CO2 -> R.string.co2
        Measure.Volatiles -> R.string.volatiles
    }
}

@Preview
@Composable
fun ChartScreenPreview() {
    val navController = rememberNavController()
    TelemetryReaderTheme()
    {
        ChartScreen(navController = navController, filename = "losdelfondo-2022-3-25.csv")
    }
}