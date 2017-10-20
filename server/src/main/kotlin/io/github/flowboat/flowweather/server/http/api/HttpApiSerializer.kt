package io.github.flowboat.flowweather.server.http.api

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.flowboat.flowweather.shared.DbObj
import io.github.flowboat.flowweather.util.CharsetConstants
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlin.reflect.full.findAnnotation

//TODO SHARED CLASS
class HttpApiSerializer {
    val charset = CharsetConstants.UTF_8.charset

    val gson = GsonBuilder().setPrettyPrinting()
            .registerTypeHierarchyAdapter(DbObj::class.java, ApiInterfaceSerializer())
            .registerTypeHierarchyAdapter(Map::class.java, KotlinMapSerializer())
            .registerTypeHierarchyAdapter(List::class.java, KotlinListSerializer())
            .create()

    inline fun <reified T : Any> serializeToString(obj: T): String =
            if(isKtSerializable<T>())
                JSON.stringify(obj)
            else
                gson.toJson(obj, typetoken<T>().type)

    inline fun <reified T : Any> parseFromString(data: String): T =
            if(isKtSerializable<T>())
                JSON.parse(data)
            else
                gson.fromJson(data, typetoken<T>().type)

    inline fun <reified T : Any> serialize(obj: T): ByteArray
        = serializeToString(obj).toByteArray(charset)

    inline fun <reified T : Any> parse(data: ByteArray): T
        = parseFromString(data.toString(charset))

    inline fun <reified T : Any> typetoken() = object : TypeToken<T>() {}

    inline fun <reified T : Any> isKtSerializable()
            = T::class.findAnnotation<Serializable>() != null
}