package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.DeviceReportTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.util.DbIdBinding

class DbDeviceReport(id: EntityID<Long>): LongEntity(id), DeviceReport {
    companion object : LongEntityClass<DbDeviceReport>(DeviceReportTable)

    override var userId by DeviceReportTable.userId
    override var deviceId by DeviceReportTable.deviceId
    override var date by DeviceReportTable.date

    override val dbId by DbIdBinding(id)

    val sensors by DbSensor referrersOn SensorTable.report
}