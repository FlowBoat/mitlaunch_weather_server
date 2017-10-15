package io.github.flowboat.flowweather.webui.pages.reportList

import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.webui.api.HttpApiProvider
import io.github.flowboat.flowweather.webui.pages.base.BasePage
import xyz.nulldev.kdom.api.Component

class ReportViewer(val report: DeviceReport) : BasePage("Report Viewer") {
    private val sensorEntries = componentList<Component>()

    //language=html
    override fun dom() = """
<nav style="padding-top: 8px" class="mdc-list-group">
    $sensorEntries
</nav>
        """.toDom()

    override suspend fun onAttach() {
        HttpApiProvider.getSensors(report).forEach {
            sensorEntries += SensorComponent(it)
        }
    }
}