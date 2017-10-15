package io.github.flowboat.flowweather.shared.sensor

import io.github.flowboat.flowweather.shared.DbObj

interface Sensor : DbObj {
    var name: String
    var vendor: String
    var power: Double
}