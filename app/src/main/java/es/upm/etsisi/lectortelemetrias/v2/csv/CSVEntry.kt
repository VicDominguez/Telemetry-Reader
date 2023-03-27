package es.upm.etsisi.lectortelemetrias.v2.csv

/**
 * Formato de las filas de los csv
 * @param idNodo: String con el id del nodo que produjo el dato
 * @param timestamp: Marca de tiempo del registro
 * @param temperatura: Temperatura recogida del sensor
 * @param humedad: Humedad recogida del sensor
 * @param co2: C02 recogida del sensor
 * @param volatiles
 */
data class CSVEntry(
    val idNodo : String,
    val timestamp: Long,
    val temperatura: Float,
    val humedad: Float,
    val co2: Float,
    val volatiles: Float
)
