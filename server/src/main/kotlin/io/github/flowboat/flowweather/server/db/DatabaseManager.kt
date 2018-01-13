package io.github.flowboat.flowweather.server.db

import io.github.flowboat.flowweather.server.db.models.tables.DeviceReportTable
import io.github.flowboat.flowweather.server.db.models.tables.ReportEntryTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager {
    init {
        //Connect to DB on initialize
        connect()

        //Create tables
        createTables()
    }

    fun connect() {
        //TODO Make this configurable
//        Database.connect("jdbc:sqlite:file:fw", "org.sqlite.JDBC")
        Database.connect("jdbc:mysql://192.168.1.102/weather",
                "com.mysql.jdbc.Driver",
                "weather",
                "" +
                        "GJzUQBoQVLDFc75E")

        //SQLITE ONLY
//        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

    fun createTables() {
        transaction {
            create(
                    DeviceReportTable,
                    ReportEntryTable
            )
        }
    }
}