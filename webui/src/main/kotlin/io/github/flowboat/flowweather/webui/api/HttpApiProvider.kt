package io.github.flowboat.flowweather.webui.api

import io.github.flowboat.flowweather.webui.api.report.ReportProvider

object HttpApiProvider : ApiProvider,
        ReportProvider {
    override val baseUrl = "/api/v1/webui"
}