package io.github.flowboat.flowweather.shared.sensor

import io.github.flowboat.flowweather.shared.DbObj

interface ReportEntry : DbObj {
    var time: Long

    var temp: Double
    var pressure: Double
    var humidity: Double
    var windSpeed: Double
}