package io.github.flowboat.flowweather.server

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import io.github.flowboat.flowweather.server.db.DatabaseManager
import io.github.flowboat.flowweather.server.http.HttpModule
import io.github.flowboat.flowweather.server.http.HttpServer

class FWServer {
    fun run(args: Array<String>) {
        //Forcibly initiate DB manager here (to ensure connected to DB)
        val dbManager = DatabaseManager()

        //Setup DI
        Kodein.global.addImport(ServerModule.create(dbManager))
        Kodein.global.addImport(HttpModule.create())

        //Boot HTTP server
        HttpServer().boot()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            FWServer().run(args)
        }
    }
}