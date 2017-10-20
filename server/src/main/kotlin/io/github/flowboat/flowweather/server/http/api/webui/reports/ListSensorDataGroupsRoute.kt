package io.github.flowboat.flowweather.server.http.api.webui.reports

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.server.db.models.dao.DbSensorGroup
import io.github.flowboat.flowweather.server.db.models.tables.SensorDataGroupTable
import io.github.flowboat.flowweather.server.http.api.HttpApiSerializer
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.ktor.pipeline.PipelineInterceptor
import org.jetbrains.ktor.response.respond

class ListSensorDataGroupsRoute {
    private val serializer: HttpApiSerializer by Kodein.global.lazy.instance()

    val handler: PipelineInterceptor<Unit> = {
        val report = call.request.queryParameters["report"]?.toLongOrNull()
                //TODO Better API errors!
            ?: throw IllegalArgumentException("Invalid report!")

        val values = transaction {
            DbSensorGroup.find {
                SensorDataGroupTable.report eq report
            }.toList().toTypedArray()
        }

        call.respond(serializer.serialize(values))
    }
}