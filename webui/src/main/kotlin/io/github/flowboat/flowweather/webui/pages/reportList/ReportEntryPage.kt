package io.github.flowboat.flowweather.webui.pages.reportList

import io.github.flowboat.flowweather.shared.sensor.ReportEntry
import io.github.flowboat.flowweather.webui.util.parseDate
import xyz.nulldev.kdom.api.Component

class ReportEntryPage(val entry: ReportEntry): Component() {
    private val entries = componentList<SensorComponent>()
    private val loaderVisibility = field("block")

    //language=html
    override fun dom() = """
<div>
    <div style="display:$loaderVisibility; padding: 16px 8px">
        <h1 class="mdc-typography--display3 mdc-typography--adjust-margin">Please wait, this page is still loading...</h1>
    </div>
    <nav style="padding-top: 8px" class="mdc-list-group">
        $entries
    </nav>
</div>
        """.toDom()

    override suspend fun onCompile() {
        update()
    }

    private fun update() {
        loaderVisibility("none")
        entries.clear()
        entries += SensorComponent("Date/time", "", parseDate(entry.time as Double).toString())
        entries += SensorComponent("Temp", "°C", entry.temp.toString())
        entries += SensorComponent("Pressure", "kPa", entry.pressure.toString())
        entries += SensorComponent("Humidity", "%", entry.humidity.toString())
        entries += SensorComponent("Wind speed", "km/h", entry.windSpeed.toString())
    }
}