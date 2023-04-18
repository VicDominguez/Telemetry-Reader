package es.upm.etsisi.lectortelemetrias.csv

import java.io.InputStream

//https://stackoverflow.com/questions/55890980/kotlin-destructuring-more-than-five-components
private operator fun <T> List<T>.component6() = this[5]

/**
 * Procesa un csv a partir de su [inputStream]
 * @param inputStream: Flujo de entrada desde el cual leer
 * @return lista con las entradas leidas
 * @see CSVEntry
 */
fun readCsv(inputStream: InputStream): List<CSVEntry>
{
    // Obtenemos el buffered reader para leer linea a linea
    val reader = inputStream.bufferedReader()
    // Leemos la primera linea
    val header = reader.readLine()

    // Obtenemos una secuencia de lineas, quitamos las vacias y mapeamos cada linea al objeto csv
    return reader
        .lineSequence()
        .filter { it.isNotBlank() }
        .map { line ->
            val (idNodo, timestamp, temperatura, humedad, co2, volatiles) =
                line.split(';', ignoreCase = false, limit = 6) //se corta la linea
            CSVEntry(idNodo, timestamp.toLong(), temperatura.toFloat(),
                humedad.toFloat(), co2.toFloat(), volatiles.toFloat())
        }.toList()
}


