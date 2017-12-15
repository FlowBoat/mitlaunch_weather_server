package io.github.flowboat.flowweather.server.http.api.webui.reports

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.server.db.models.dao.DbDeviceReport
import io.github.flowboat.flowweather.server.http.api.HttpApiSerializer
import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.ktor.pipeline.PipelineInterceptor
import org.jetbrains.ktor.response.respond

class ListReportsRoute {
    private val serializer: HttpApiSerializer by Kodein.global.lazy.instance()

    val handler: PipelineInterceptor<Unit> = {
        val reports = transaction {
            DbDeviceReport.all().toList().toTypedArray()
        }

        call.respond(serializer.serialize(reports))
    }
}
