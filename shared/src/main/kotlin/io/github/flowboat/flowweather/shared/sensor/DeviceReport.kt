package io.github.flowboat.flowweather.shared.sensor

import io.github.flowboat.flowweather.shared.DbObj

interface DeviceReport : DbObj {
    var userId: Long
    var deviceId: Long
    var uploadDate: Long
}