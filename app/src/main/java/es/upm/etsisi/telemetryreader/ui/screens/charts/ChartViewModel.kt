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

class ChartViewModel(assetManager: AssetManager, filename: String) : ViewModel()
{
    // Factoria para poder crear el ViewModel con los parámetros correspondientes
    class Factory(private val assetManager: AssetManager, 
                  private val filename: String) 
        : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T 
        {
            return ChartViewModel(assetManager, filename) as T
        }
    }
    
    // Lista con los registros del csv
    private val data : List<CSVEntry> = readCsv(assetManager.open("csv/$filename"))

    // Estado
    // Por defecto hacemos que muestre la temperatura
    private val _state = MutableStateFlow(Measure.Temperature)
    val state: StateFlow<Measure> = _state.asStateFlow()

    //Productor
    val producer = ChartEntryModelProducer()

    // Ejecuamos funciones al arrancar. En este caso meter en el productor los datos correspondientes
    init
    {
        updateProducer()
    }

    /**
     * Función para cambiar el estado
     */
    fun onCategoryChange(category: Measure)
    {
        _state.value = category
        updateProducer()
    }

    /**
     * Actualización del productor de datos según la categoria
     */
    private fun updateProducer()
    {
        // Segun la categoria metemos en las y unos datos u otros
        val entries = when(_state.value) {
            Measure.Temperature -> data.mapIndexed { index, csvEntry ->
                Entry(
                    timestamp = csvEntry.timestamp,
                    x = index.toFloat(),
                    y = csvEntry.temperatura
                )
            }

            Measure.Humidity -> data.mapIndexed { index, csvEntry ->
                Entry(
                    timestamp = csvEntry.timestamp,
                    x = index.toFloat(),
                    y = csvEntry.humedad
                )
            }

            Measure.CO2 -> data.mapIndexed { index, csvEntry ->
                Entry(
                    timestamp = csvEntry.timestamp,
                    x = index.toFloat(),
                    y = csvEntry.co2
                )
            }

            Measure.Volatiles -> data.mapIndexed { index, csvEntry ->
                Entry(
                    timestamp = csvEntry.timestamp,
                    x = index.toFloat(),
                    y = csvEntry.volatiles
                )
            }
        }
        // Actualizamos las entradas del productor
        producer.setEntries(entries)
    }
}