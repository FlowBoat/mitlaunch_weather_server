package io.github.flowboat.flowweather.server.http.api.app.reports.model

import kotlinx.serialization.Serializable

//TODO SHARED CLASS!
@Serializable
data class SerializableReportEntry(val time: Long,

                                   val temp: Double,
                                   val pressure: Double,
                                   val humidity: Double,
                                   val windSpeed: Double)