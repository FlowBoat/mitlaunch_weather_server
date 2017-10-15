package io.github.flowboat.flowweather.server.http.api

import com.google.gson.*
import java.lang.reflect.Type
import kotlin.reflect.KProperty1
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.declaredMemberProperties

class ApiInterfaceSerializer : JsonSerializer<Any> {
    override fun serialize(src: Any?, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        if(src == null) {
            return JsonNull.INSTANCE
        }

        val values: List<KProperty1<Any, Any?>> = (src::class.allSuperclasses + src::class).filter {
            it.java.isInterface
        }.flatMap {
            it.declaredMemberProperties
        } as List<KProperty1<Any, Any?>>

        val out = JsonObject()

        values.forEach {
            out.add(it.name, context.serialize(it.get(src)))
        }

        return out
    }
}