package io.github.flowboat.flowweather.webui.api.report

import io.github.flowboat.flowweather.webui.api.ApiProvider
import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.shared.sensor.Sensor
import io.github.flowboat.flowweather.shared.sensor.SensorValue

interface ReportProvider : ApiProvider {
    suspend fun getReports(): Array<DeviceReport> {
        return get("/list-reports")
    }

    suspend fun getSensors(report: DeviceReport): Array<Sensor> {
        return get("/list-sensors?report=${report.dbId}")
    }

    suspend fun getSensorValues(sensor: Sensor): Array<SensorValue> {
        return get("/list-sensor-values?sensor=${sensor.dbId}")
    }
}