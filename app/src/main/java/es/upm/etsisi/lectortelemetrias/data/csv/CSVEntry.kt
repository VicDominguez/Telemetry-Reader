package es.upm.etsisi.lectortelemetrias.data.csv

data class CSVEntry(
    val idNodo : String,
    val timestamp: Long,
    val temperatura: Float,
    val humedad: Float,
    val co2: Float,
    val volatiles: Float
)
