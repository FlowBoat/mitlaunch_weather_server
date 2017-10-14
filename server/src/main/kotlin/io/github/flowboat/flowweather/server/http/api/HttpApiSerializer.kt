package io.github.flowboat.flowweather.server.http.api

import io.github.flowboat.flowweather.util.CharsetConstants
import kotlinx.serialization.json.JSON

//TODO SHARED CLASS
class HttpApiSerializer {
    val charset = CharsetConstants.UTF_8.charset

    inline fun <reified T : Any> serialize(obj: T): ByteArray =
            JSON.stringify(obj).toByteArray(charset)

    inline fun <reified T : Any> parse(data: ByteArray): T =
            JSON.parse(data.toString(charset))
}