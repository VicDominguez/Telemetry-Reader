package es.upm.etsisi.lectortelemetrias.ui.screens.charts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.horizontal.topAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import es.upm.etsisi.lectortelemetrias.data.csv.readCsv

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(navController: NavController, filename: String)
{
    val data = readCsv(LocalContext.current.assets.open("csv/$filename"))
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp))
    {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top))
        {
            val chartEntryModel = entryModelOf(data.mapIndexed{index, value -> FloatEntry((index+1).toFloat(),value.humedad) })
            val chartStyle = m3ChartStyle()

            ProvideChartStyle(chartStyle)
            {
                Chart(
                    chart = lineChart(),
                    model = chartEntryModel,
                    startAxis = startAxis(
                        titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                        title = "Humedad"
                    ),
                    topAxis = topAxis(
                        label = null,
                        axis = null,
                        tick = null,
                        guideline = null,
                        titleComponent = textComponent(color = chartStyle.axis.axisLabelColor),
                        title = filename
                    ),
                    bottomAxis = bottomAxis(),
                )
            }

        }
    }
}