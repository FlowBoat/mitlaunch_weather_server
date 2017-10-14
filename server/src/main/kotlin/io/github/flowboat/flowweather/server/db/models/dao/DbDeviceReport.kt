package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.DeviceReportTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class DbDeviceReport(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<DbDeviceReport>(DeviceReportTable)

    var userId by DeviceReportTable.userId
    var deviceId by DeviceReportTable.deviceId
    var date by DeviceReportTable.date

    val sensors by DbSensor referrersOn SensorTable.report
}