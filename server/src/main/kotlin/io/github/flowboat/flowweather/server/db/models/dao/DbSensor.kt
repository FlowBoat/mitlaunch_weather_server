package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.SensorTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorValueTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class DbSensor(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<DbSensor>(SensorTable)

    var report by DbDeviceReport referencedOn SensorTable.report

    var name by SensorTable.name
    var vendor by SensorTable.vendor
    var power by SensorTable.power

    val values by DbSensorValue referrersOn SensorValueTable.sensor
}