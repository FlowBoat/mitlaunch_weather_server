package io.github.flowboat.flowweather.webui.util

//Wrap JS objects as maps
fun <V> PrimitiveHashMap(container: dynamic): MutableMap<String, V> {
    val m = mutableMapOf<String, V>()
    val keys = js("Object.keys")
    (keys(container) as Array<String>).forEach {
        m[it] = container[it]
    }
    return m
}