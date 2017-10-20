package io.github.flowboat.flowweather.server.http.api

import com.google.gson.*
import java.lang.reflect.Type

class KotlinMapSerializer : JsonSerializer<Any> {
    override fun serialize(src: Any?, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        val casted = src as? Map<*, *> ?: return JsonNull.INSTANCE

        val root = JsonObject()
        casted.forEach { (k, v) ->
            root.add(k.toString(), context.serialize(v))
        }

        return root
    }
}