package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.ReportEntryTable
import io.github.flowboat.flowweather.shared.sensor.ReportEntry
import io.github.flowboat.flowweather.util.DbIdBinding
import io.github.flowboat.flowweather.util.finiteOrNegativeOne
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import java.math.BigDecimal

class DbReportEntry(id: EntityID<Long>): LongEntity(id), ReportEntry {
    companion object : LongEntityClass<DbReportEntry>(ReportEntryTable)

    var report by DbDeviceReport referencedOn ReportEntryTable.report

    override var time by ReportEntryTable.time

    override var temp: Double
        get() { return tempBD.toDouble() }
        set(value) { tempBD = BigDecimal(value.finiteOrNegativeOne()) }
    var tempBD by ReportEntryTable.temp

    override var pressure: Double
        get() { return pressureBD.toDouble() }
        set(value) { pressureBD = BigDecimal(value.finiteOrNegativeOne()) }
    var pressureBD by ReportEntryTable.pressure

    override var humidity: Double
        get() { return humidityBD.toDouble() }
        set(value) { humidityBD = BigDecimal(value.finiteOrNegativeOne()) }
    var humidityBD by ReportEntryTable.humidity

    override var windSpeed: Double
        get() { return windSpeedBD.toDouble() }
        set(value) { windSpeedBD = BigDecimal(value.finiteOrNegativeOne()) }
    var windSpeedBD by ReportEntryTable.windSpeed

    override val dbId by DbIdBinding(id)
}