package io.github.flowboat.flowweather.server.db

import io.github.flowboat.flowweather.server.db.models.tables.DeviceReportTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorTable
import io.github.flowboat.flowweather.server.db.models.tables.SensorValueTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class DatabaseManager {
    init {
        //Connect to DB on initialize
        connect()

        //Create tables
        createTables()
    }

    fun connect() {
        //TODO Make this configurable
        Database.connect("jdbc:sqlite:file:fw", "org.sqlite.JDBC")

        //SQLITE ONLY
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

    fun createTables() {
        transaction {
            create(
                    DeviceReportTable,
                    SensorTable,
                    SensorValueTable
            )
        }
    }
}