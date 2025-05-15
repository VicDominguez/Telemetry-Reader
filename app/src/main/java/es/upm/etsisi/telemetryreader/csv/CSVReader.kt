package es.upm.etsisi.telemetryreader.csv

import java.io.InputStream

private operator fun <T> List<T>.component6() = this[5]

/**
 * Parses a csv from an [inputStream]
 * @param inputStream: Input stream of where to read
 * @return List with the read entries
 * @see CSVEntry
 */
fun readCsv(inputStream: InputStream): List<CSVEntry> {
    // Obtain buffered reader to read line by line
    val reader = inputStream.bufferedReader()
    
    // Read header line
    reader.readLine()

    // Map line by line removing blank lines and parsing each line to object
    return reader
        .lineSequence()
        .filter { it.isNotBlank() }
        .map { line ->
            val (id, timestamp, temperature, humidity, co2, volatiles) = line.split(';', ignoreCase = false, limit = 6)
            CSVEntry(
                idNode = id,
                timestamp = timestamp.toLong(),
                temperature = temperature.toFloat(),
                humidity = humidity.toFloat(),
                co2 = co2.toFloat(),
                volatiles = volatiles.toFloat()
            )
        }.toList()
}