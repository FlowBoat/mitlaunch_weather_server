package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.SensorValueTable
import io.github.flowboat.flowweather.shared.sensor.SensorValue
import io.github.flowboat.flowweather.util.DbIdBinding
import io.github.flowboat.flowweather.util.finiteOrNegativeOne
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

class DbSensorValue(id: EntityID<Long>): LongEntity(id), SensorValue {
    companion object : LongEntityClass<DbSensorValue>(SensorValueTable)

    var group by DbSensorGroup referencedOn SensorValueTable.group
    var sensor by DbSensor referencedOn SensorValueTable.sensor

    override var index by SensorValueTable.index
    override var value: Double
        get() { return valueBD.toDouble() }
        set(value) { valueBD = BigDecimal(value.finiteOrNegativeOne()) }

    var valueBD by SensorValueTable.value

    override val dbId by DbIdBinding(id)
    override val sensorId
        get() = transaction {
            sensor.id.value
        }
}