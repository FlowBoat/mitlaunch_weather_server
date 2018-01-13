package io.github.flowboat.flowweather.server.http.api.app.reports

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.server.db.models.dao.DbDeviceReport
import io.github.flowboat.flowweather.server.db.models.dao.DbReportEntry
import io.github.flowboat.flowweather.server.http.api.HttpApiSerializer
import io.github.flowboat.flowweather.server.http.api.app.reports.model.DeviceReport
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.ktor.pipeline.PipelineInterceptor

class SubmitReportRoute {
    private val serializer: HttpApiSerializer by Kodein.global.lazy.instance()

    val handler: PipelineInterceptor<Unit> = {
        val reportBytes = call.request.receiveContent().inputStream().use {
            it.readBytes()
        }

        val report = serializer.parse<DeviceReport>(reportBytes)

        println("Inserting report...")

        //Insert report into DB
        transaction {
            val dbReport = DbDeviceReport.new {
                userId = 0
                deviceId = report.deviceId
                uploadDate = System.currentTimeMillis()
            }

            report.data.forEach {
                DbReportEntry.new {
                    this.report = dbReport

                    time = it.time

                    temp = it.temp
                    pressure = it.pressure
                    humidity = it.humidity
                    windSpeed = it.windSpeed
                }
            }
        }

        //TODO Logging
        println("Report inserted!")
    }
}