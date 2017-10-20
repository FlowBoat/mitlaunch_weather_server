package io.github.flowboat.flowweather.server.db.models.dao

import io.github.flowboat.flowweather.server.db.models.tables.SensorValueTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorDataGroupTable
import io.github.flowboat.flowweather.shared.sensor.SensorDataGroup
import io.github.flowboat.flowweather.util.DbIdBinding
import io.github.flowboat.flowweather.util.RFC2822_DATE_FORMATTER
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import java.time.ZonedDateTime

class DbSensorGroup(id: EntityID<Long>): LongEntity(id), SensorDataGroup {
    companion object : LongEntityClass<DbSensorGroup>(SensorDataGroupTable)

    var report by DbDeviceReport referencedOn SensorDataGroupTable.report

    var time by SensorDataGroupTable.time
    override var jsTime
        get() = ZonedDateTime.parse(time).format(RFC2822_DATE_FORMATTER)
        set(value) {
            time = ZonedDateTime.parse(value, RFC2822_DATE_FORMATTER).toString()
        }

    override val dbId by DbIdBinding(id)

    val values by DbSensorValue referrersOn SensorValueTable.group
}