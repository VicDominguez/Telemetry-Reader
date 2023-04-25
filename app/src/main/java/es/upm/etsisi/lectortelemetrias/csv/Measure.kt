package es.upm.etsisi.lectortelemetrias.csv

import android.util.Range

/**
 * Tipos de medidas recogidas por el sistema
 */
enum class Measure(val range: Range<Float>?) {
    Temperature(Range(0f, 40f)),
    Humidity(Range(0f, 100f)),
    // 400 - 32768 rango completo
    // https://www.sciosense.com/wp-content/uploads/documents/SC-001232-DS-3-CCS811B-Datasheet-Revision-2.pdf
    CO2(null),
    Volatiles(null),
}