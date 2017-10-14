package io.github.flowboat.flowweather.server

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.github.flowboat.flowweather.server.db.DatabaseManager

class ServerModule {
    companion object {
        fun create() = Kodein.Module {
            //Forcibly initiate DB manager here (to ensure connected to DB)
            val dbManager = DatabaseManager()

            bind<DatabaseManager>() with singleton { dbManager }
        }
    }
}