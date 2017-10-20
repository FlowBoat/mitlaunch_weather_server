package io.github.flowboat.flowweather.server.db.models.tables

import org.jetbrains.exposed.dao.LongIdTable

object SensorDataGroupTable : LongIdTable() {
    val report = reference("report", DeviceReportTable)
    val time = varchar("time", 200)
}
