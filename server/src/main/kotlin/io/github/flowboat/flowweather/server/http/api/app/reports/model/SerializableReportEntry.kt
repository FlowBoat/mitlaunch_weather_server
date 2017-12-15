package io.github.flowboat.flowweather.server.http.api.app.reports.model

data class SerializableReportEntry(val time: Long,

                                   val temp: Double,
                                   val pressure: Double,
                                   val humidity: Double,
                                   val windSpeed: Double)