package io.github.flowboat.flowweather.server.db.models.tables

import org.jetbrains.exposed.dao.LongIdTable

object SensorValueTable : LongIdTable() {
    val sensor = reference("sensor", SensorTable)

    val index = integer("value_index")
    //TODO Precision and scale?
    val value = decimal("value", 20, 10)
}