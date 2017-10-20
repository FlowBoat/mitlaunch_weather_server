package io.github.flowboat.flowweather.webui.api.report

import io.github.flowboat.flowweather.shared.sensor.*
import io.github.flowboat.flowweather.webui.api.ApiProvider

interface ReportProvider : ApiProvider {
    suspend fun getReports(): Array<DeviceReport> {
        return get("/list-reports")
    }

    suspend fun getSensors(report: DeviceReport): Array<Sensor> {
        return get("/list-sensors?report=${report.dbId}")
    }

    suspend fun getSensorDataGroups(report: DeviceReport): Array<SensorDataGroup> {
        return get("/list-sensor-data-groups?report=${report.dbId}")
    }

    suspend fun getSensorValues(group: SensorDataGroup): Array<SensorValue> {
        return get("/list-sensor-values?group=${group.dbId}")
    }
}