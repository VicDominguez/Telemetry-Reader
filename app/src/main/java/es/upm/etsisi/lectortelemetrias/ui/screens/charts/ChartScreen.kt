package es.upm.etsisi.lectortelemetrias.ui.screens.charts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import es.upm.etsisi.lectortelemetrias.R
import es.upm.etsisi.lectortelemetrias.csv.Measure
import es.upm.etsisi.lectortelemetrias.ui.theme.LectorTelemetriasTheme
import es.upm.etsisi.lectortelemetrias.ui.utils.Entry
import es.upm.etsisi.lectortelemetrias.ui.utils.rememberMarker
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(navController: NavController, filename: String)
{
    // ViewModel de la pantalla
    val viewModel : ChartViewModel = viewModel(
        factory = ChartViewModel.Factory(
            assetManager = LocalContext.current.assets,
            filename = filename
        )
    )

    // Estado
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize(),
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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it), //padding para que no se superponga la cabecera
        )
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                DisplayCategoryMenu(label = stringResource(id = R.string.category),
                    options = Measure.values(),
                    selected = state,
                    onChange = {measure -> viewModel.onCategoryChange(measure)})
                DisplayChart(state = state, producer = viewModel.producer)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayCategoryMenu(label: String,
                                options: Array<Measure>,
                                selected : Measure,
                                onChange: (Measure) -> Unit,
                                modifier: Modifier = Modifier,
)
{
    var expanded by remember { mutableStateOf(false) }

    // Punto de entrada al menú
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // Elemento visible del menú en todo momento
        TextField(
            // El `menuAnchor` se le tiene que pasar para que se vea correctamente el campo.
            modifier = Modifier.menuAnchor(),
            // Campo de solo lectura
            readOnly = true,
            // Texto a mostrar
            value = stringResource(id = selected.label()),
            // Como no hay cambio de valor al ser solo lectura, la función es vacía
            onValueChange = {},
            // Encabezado de la sección
            label = { Text(text = label) },
            // Icono en el campo de texto
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            // Colores del campo de texto
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )

        // Opciones del menú
        ExposedDropdownMenu(
            expanded = expanded,
            // Para cerrar el desplegable al pulsar fuera
            onDismissRequest = { expanded = false },
        ) {
            // Para cada medida hay una opción
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    // Texto para cada opción
                    text = { Text(text = stringResource(id = selectionOption.label())) },
                    onClick = {
                        // Al pulsar se cierra el menú y se cambie en el viewmodel la opción
                        expanded = false
                        onChange(selectionOption)
                    },
                    // Padding del desplegable
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun DisplayChart(state: Measure,
                 producer : ChartEntryModelProducer)
{
    // Estilo de material design 3
    val chartStyle = m3ChartStyle()

    // Se le recuerda porque no va a mutar entre los cambios
    val dtf = remember { DateTimeFormatter.ofPattern("HH:mm:ss") }

    val valueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { index, chartValues ->
        // Con el indice se accede al listado de entradas y con el timestamp se saca su formateo
        (chartValues.chartEntryModel.entries.first().getOrNull(index.toInt()) as? Entry)
            ?.timestamp
            ?.run { dtf.format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())) }
            .orEmpty()
    }

    ProvideChartStyle(chartStyle)
    {
        Chart(
            // Diagrama de linea con más espacio para que entre la marca de texto
            chart = lineChart(
                // Espacio más grande para que entre la etiqueta de la hora
                spacing = 48.dp,
                // Solo sobreescribimos los rangos del eje Y si hay rango para sobreescribir
                axisValuesOverrider = state.range?.let {
                    AxisValuesOverrider.fixed(null,null,it.lower,it.upper)
                },
            ),
            // Modelo de datos sacado del productor
            model = producer.getModel(),
            // Ocupar todo el espacio libre
            modifier = Modifier.fillMaxSize(),
            // Eje de la izq tiene como titulo la categoria
            startAxis = startAxis(
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(id = state.label())
            ),
            bottomAxis = bottomAxis(
                // Reducimos el tamaño del texto
                label = axisLabelComponent(textSize = 10f.sp),
                // Formateador para poner a cada dato su fecha
                valueFormatter = valueFormatter,
                // El titulo del eje es timestamp
                titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                title = stringResource(id = R.string.timestamp)
            ),
            // Poner un marcador cuando se pulsa un registro
            marker = rememberMarker()
        )
    }
}

fun Measure.label(): Int
{
    return when(this)
    {
        Measure.Humidity -> R.string.humidity
        Measure.Temperature -> R.string.temperature
        Measure.CO2 -> R.string.co2
        Measure.Volatiles -> R.string.volatiles
    }
}

@Preview
@Composable
fun ChartScreenPreview()
{
    val navController = rememberNavController()
    LectorTelemetriasTheme()
    {
        ChartScreen(navController = navController, filename = "losdelfondo-2022-3-25.csv")
    }
}