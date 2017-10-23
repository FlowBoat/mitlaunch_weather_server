package io.github.flowboat.flowweather.server.http.api.app.reports

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.server.db.models.dao.DbDeviceReport
import io.github.flowboat.flowweather.server.db.models.dao.DbSensor
import io.github.flowboat.flowweather.server.db.models.dao.DbSensorValue
import io.github.flowboat.flowweather.server.db.models.dao.DbSensorGroup
import io.github.flowboat.flowweather.server.db.models.tables.SensorTable
import io.github.flowboat.flowweather.server.http.api.HttpApiSerializer
import io.github.flowboat.flowweather.server.http.api.app.reports.model.DeviceReport
import io.github.flowboat.flowweather.server.http.api.app.reports.model.SerializableSensor
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.ktor.pipeline.PipelineInterceptor
import org.joda.time.LocalDateTime

class SubmitReportRoute {
    private val serializer: HttpApiSerializer by Kodein.global.lazy.instance()

    val handler: PipelineInterceptor<Unit> = {
        val reportBytes = call.request.receiveContent().inputStream().use {
            it.readBytes()
        }

        val report = serializer.parse<DeviceReport>(reportBytes)

//        println("Inserting report: $report")
        println("Inserting report...")

        //Insert report into DB
        transaction {
            val dbReport = DbDeviceReport.new {
                userId = 0
                deviceId = 0
                date = LocalDateTime.now().toString()
            }

            //Required since DB operations are batched so we can't perform sensor dedupe in the DB
            val sensorCache = mutableMapOf<String, DbSensor>()
            fun sensorCacheKey(name: String, vendor: String) = "{$name} -> {$vendor}"
            fun sensorCachePut(sensor: DbSensor) {
                sensorCache[sensorCacheKey(sensor.name, sensor.vendor)] = sensor
            }
            fun sensorCacheGet(entry: SerializableSensor)
                    = sensorCache[sensorCacheKey(entry.name, entry.vendor)]

            report.sensorMap.forEach { t, u ->
                val group = DbSensorGroup.new {
                    this.report = dbReport
                    time = t
                }

                u.forEach { entry ->
                    val sensor = sensorCacheGet(entry.sensor) // Check memcache
                            ?: DbSensor.find { // Check DB
                        SensorTable.report eq dbReport and
                                SensorTable.name.eq(entry.sensor.name) and
                                SensorTable.vendor.eq(entry.sensor.vendor)
                    }.firstOrNull()
                            ?: DbSensor.new { // New sensor
                        this.report = dbReport
                        name = entry.sensor.name
                        vendor = entry.sensor.vendor
                        power = entry.sensor.power.toDouble()
                    }.apply { sensorCachePut(this) } // Put new sensor in memcache

                    entry.value.value.forEachIndexed { index, it ->
                        DbSensorValue.new {
                            this.sensor = sensor
                            this.group = group

                            this.index = index
                            value = it.toDouble()
                        }
                    }
                }
            }
        }

        //TODO Logging
        println("Report inserted!")
    }
}