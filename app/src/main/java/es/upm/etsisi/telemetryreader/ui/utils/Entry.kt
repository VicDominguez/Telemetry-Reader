package es.upm.etsisi.telemetryreader.ui.utils

import com.patrykandpatrick.vico.core.entry.ChartEntry

/**
 * Plot entry type in order to add timestamp
 */
class Entry(
    val timestamp: Long,
    override val x: Float,
    override val y: Float,
) : ChartEntry {
    override fun withY(y: Float) = Entry(timestamp, x, y)
}
