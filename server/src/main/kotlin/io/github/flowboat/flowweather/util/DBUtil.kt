package io.github.flowboat.flowweather.util

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import kotlin.reflect.KProperty

class DbIdBinding(private val id: EntityID<Long>) {
    operator fun getValue(obj: LongEntity, property: KProperty<*>): Long {
        return id.value
    }
}
