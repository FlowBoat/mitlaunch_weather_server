package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.SensorValueTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass

class DbSensorValue(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<DbSensorValue>(SensorValueTable)

    var sensor by DbSensor referencedOn SensorValueTable.sensor

    var index by SensorValueTable.index
    var value by SensorValueTable.value
}