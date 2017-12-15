package io.github.flowboat.flowweather.server.db.models.tables

import org.jetbrains.exposed.dao.LongIdTable

object ReportEntryTable : LongIdTable() {
    val report = reference("report", DeviceReportTable)

    val time = long("time")

    val temp = decimal("temp", 20, 10)
    val pressure = decimal("pressure", 20, 10)
    val humidity = decimal("humidity", 20, 10)
    val windSpeed = decimal("wind_speed", 20, 10)
}