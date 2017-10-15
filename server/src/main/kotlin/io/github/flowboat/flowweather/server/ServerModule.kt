package io.github.flowboat.flowweather.server

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.github.flowboat.flowweather.server.db.DatabaseManager

class ServerModule {
    companion object {
        fun create(databaseManager: DatabaseManager) = Kodein.Module {
            bind<DatabaseManager>() with singleton { databaseManager }
        }
    }
}