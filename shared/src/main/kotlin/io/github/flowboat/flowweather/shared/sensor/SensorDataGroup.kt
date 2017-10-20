package io.github.flowboat.flowweather.shared.sensor

import io.github.flowboat.flowweather.shared.DbObj

interface SensorDataGroup : DbObj {
    var jsTime: String
}
