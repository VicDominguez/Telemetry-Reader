package es.upm.etsisi.telemetryreader.csv

import android.util.Range

/**
 * Set ranges of each measure to enhance plot
 */
enum class Measure(val range: Range<Float>?) {
    Temperature(Range(0f, 40f)),
    Humidity(Range(0f, 100f)),
    CO2(null),
    Volatiles(null),
}