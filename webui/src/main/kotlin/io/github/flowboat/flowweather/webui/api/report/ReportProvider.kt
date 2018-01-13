package io.github.flowboat.flowweather.webui.api.report

import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.shared.sensor.ReportEntry
import io.github.flowboat.flowweather.webui.api.ApiProvider

interface ReportProvider : ApiProvider {
    suspend fun getReports(): Array<DeviceReport> {
        return get("/list-reports")
    }

    suspend fun getReportEntries(report: DeviceReport): Array<ReportEntry> {
        return get("/list-report-entries?report=${report.dbId}")
    }
}