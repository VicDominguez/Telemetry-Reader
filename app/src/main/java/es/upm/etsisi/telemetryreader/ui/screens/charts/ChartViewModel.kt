package es.upm.etsisi.telemetryreader.ui.screens.charts

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import es.upm.etsisi.telemetryreader.csv.CSVEntry
import es.upm.etsisi.telemetryreader.csv.Measure
import es.upm.etsisi.telemetryreader.csv.readCsv
import es.upm.etsisi.telemetryreader.ui.utils.Entry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChartViewModel(assetManager: AssetManager, filename: String) : ViewModel() {
    // Factory to create a parameterized viewmodel
    class Factory(
        private val assetManager: AssetManager,
        private val filename: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChartViewModel(assetManager, filename) as T
        }
    }

    private val data: List<CSVEntry> = readCsv(assetManager.open("csv/$filename"))

    // Plot temperature by default
    private val _state = MutableStateFlow(Measure.Temperature)
    val state: StateFlow<Measure> = _state.asStateFlow()

    val producer = ChartEntryModelProducer()

    init {
        updateProducer()
    }

    fun onCategoryChange(category: Measure) {
        _state.value = category
        updateProducer()
    }

    private fun updateProducer() {
        // Fill data depending on category selected
        val entries = data.mapIndexed { index, csvEntry ->

            val measureToPlot = when (_state.value) {
                Measure.Temperature -> csvEntry.temperature
                Measure.Humidity -> csvEntry.humidity
                Measure.CO2 -> csvEntry.co2
                Measure.Volatiles -> csvEntry.volatiles
            }

            Entry(
                timestamp = csvEntry.timestamp,
                x = index.toFloat(),
                y = measureToPlot
            )
        }
        producer.setEntries(entries)
    }
}