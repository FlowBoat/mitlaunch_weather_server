package io.github.flowboat.flowweather.server.db.models.tables

import org.jetbrains.exposed.dao.LongIdTable

object SensorTable : LongIdTable() {
    val report = reference("report", DeviceReportTable)

    val name = varchar("name", 200)
    val vendor = varchar("vendor", 200)

    //TODO Precision and scale?
    val power = decimal("power", 20, 10)
}