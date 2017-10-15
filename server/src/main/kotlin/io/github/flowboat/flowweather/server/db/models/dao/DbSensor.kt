package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.SensorTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorValueTable
import io.github.flowboat.flowweather.shared.sensor.Sensor
import io.github.flowboat.flowweather.util.DbIdBinding
import io.github.flowboat.flowweather.util.finiteOrNegativeOne
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import java.math.BigDecimal

class DbSensor(id: EntityID<Long>): LongEntity(id), Sensor {
    companion object : LongEntityClass<DbSensor>(SensorTable)

    var report by DbDeviceReport referencedOn SensorTable.report

    override var name by SensorTable.name
    override var vendor by SensorTable.vendor
    override var power: Double
        get() = powerBD.toDouble()
        set(value) { powerBD = BigDecimal(value.finiteOrNegativeOne()) }

    var powerBD by SensorTable.power

    override val dbId by DbIdBinding(id)

    val values by DbSensorValue referrersOn SensorValueTable.sensor
}