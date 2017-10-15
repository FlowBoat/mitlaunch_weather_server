package io.github.flowboat.flowweather.server.http.api.app.reports

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.server.db.models.dao.DbDeviceReport
import io.github.flowboat.flowweather.server.db.models.dao.DbSensor
import io.github.flowboat.flowweather.server.db.models.dao.DbSensorValue
import io.github.flowboat.flowweather.server.http.api.HttpApiSerializer
import io.github.flowboat.flowweather.server.http.api.app.reports.model.DeviceReport
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

        println("Inserting report: $report")

        //Insert report into DB
        transaction {
            val dbReport = DbDeviceReport.new {
                userId = 0
                deviceId = 0
                date = LocalDateTime.now().toString()
            }

            report.sensorMap.forEach { t, u ->
                val sensor = DbSensor.new {
                    this.report = dbReport
                    name = t.name
                    vendor = t.vendor
                    power = t.power.toDouble()
                }

                u.value.forEachIndexed { index, fl ->
                    DbSensorValue.new {
                        this.sensor = sensor

                        this.index = index
                        value = fl.toDouble()
                    }
                }
            }
        }

        //TODO Logging
        println("Report inserted!")
    }
}