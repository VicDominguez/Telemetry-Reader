package es.upm.etsisi.lectortelemetrias.v2.ui.utils

import com.patrykandpatrick.vico.core.entry.ChartEntry

/**
 * Tipo de entrada para la gráfica que añade el timestamp para poder mostrarlo
 */
class Entry(
    val timestamp: Long,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = Entry(timestamp, x, y)
}
