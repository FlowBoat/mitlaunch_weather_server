package io.github.flowboat.flowweather.shared.sensor

import io.github.flowboat.flowweather.shared.DbObj

interface SensorValue : DbObj {
    var index: Int
    var value: Double
}