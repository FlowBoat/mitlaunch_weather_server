package io.github.flowboat.flowweather.webui.util

fun <T> printExceptions(block: () -> T): T {
    try {
        return block()
    } catch(e: Exception) {
        console.error(e)
        throw e
    }
}