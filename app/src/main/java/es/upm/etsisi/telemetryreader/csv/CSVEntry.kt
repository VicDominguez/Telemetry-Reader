package es.upm.etsisi.telemetryreader.csv

/**
 * Format of the csv rows
 * @param idNode: String with the id of the node that produced the data.
 * @param timestamp: Timestamp when data was read.
 * @param temperature: Temperature collected from the sensor.
 * @param humidity: Humidity collected from the sensor.
 * @param co2: C02 collected from sensor.
 * @param volatiles: A random float value
 */
data class CSVEntry(
    val idNode : String,
    val timestamp: Long,
    val temperature: Float,
    val humidity: Float,
    val co2: Float,
    val volatiles: Float
)
