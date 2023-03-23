package es.upm.etsisi.lectortelemetrias.data.csv

import java.io.InputStream

//https://stackoverflow.com/questions/55890980/kotlin-destructuring-more-than-five-components
private operator fun <T> List<T>.component6() = this[5]

fun readCsv(inputStream: InputStream): List<CSVEntry>
{
    val reader = inputStream.bufferedReader()
    val header = reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val (idNodo, timestamp, temperatura, humedad, co2, volatiles) = it.split(';', ignoreCase = false, limit = 6)
            CSVEntry(idNodo, timestamp.toLong(), temperatura.toFloat(), humedad.toFloat(), co2.toFloat(), volatiles.toFloat())
        }.toList()
}


