package io.github.flowboat.flowweather.shared.sensor

import io.github.flowboat.flowweather.shared.DbObj

interface SensorValue : DbObj {
    val sensorId: Long
    var index: Int
    var value: Double
}