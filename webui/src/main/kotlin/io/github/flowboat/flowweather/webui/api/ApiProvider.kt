package io.github.flowboat.flowweather.webui.api

import org.w3c.xhr.JSON
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType
import xyz.nulldev.kdom.api.util.await

interface ApiProvider {
    val baseUrl: String

    suspend fun String.postTo(url: String) {
        val xhr = XMLHttpRequest()
        xhr.open("POST", baseUrl + url)
        xhr.send(this)
        xhr.await()
    }

    suspend fun <T> get(url: String): T {
        val xhr = XMLHttpRequest()
        xhr.responseType = XMLHttpRequestResponseType.JSON
        xhr.open("GET", baseUrl + url)
        xhr.send()
        xhr.await()
        return xhr.response.asDynamic()
    }

    suspend fun postStringTo(string: String, url: String) {
        string.postTo(url)
    }
}