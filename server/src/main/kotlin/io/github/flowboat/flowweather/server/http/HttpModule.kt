package io.github.flowboat.flowweather.server.http

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.github.flowboat.flowweather.server.http.api.HttpApiSerializer

class HttpModule {
    companion object {
        fun create() = Kodein.Module {
            bind<HttpApiSerializer>() with singleton { HttpApiSerializer() }
        }
    }
}