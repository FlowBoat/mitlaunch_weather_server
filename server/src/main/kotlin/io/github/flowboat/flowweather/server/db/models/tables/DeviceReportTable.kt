package io.github.flowboat.flowweather.server.db.models.tables

import org.jetbrains.exposed.dao.LongIdTable

object DeviceReportTable: LongIdTable() {
    val userId = long("user_id")
    val deviceId = long("device_id")
    val date = date("date")
}