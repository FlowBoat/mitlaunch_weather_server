package io.github.flowboat.flowweather.server.http.api

import com.google.gson.*
import java.lang.reflect.Type

class KotlinListSerializer : JsonSerializer<Any> {
    override fun serialize(src: Any?, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        val casted = src as? List<*> ?: return JsonNull.INSTANCE

        val root = JsonArray()
        casted.forEach { root.add(context.serialize(it)) }

        return root
    }
}