package io.github.flowboat.flowweather.server.http.api.app.reports.model

import kotlinx.serialization.Serializable

//TODO SHARED CLASS!
@Serializable
data class DeviceReport(val deviceId: Long,
                        val uploadDate: Long,
                        val data: List<SerializableReportEntry>)

