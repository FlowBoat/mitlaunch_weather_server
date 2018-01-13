package io.github.flowboat.flowweather.webui.pages.reportList

import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.webui.api.HttpApiProvider
import io.github.flowboat.flowweather.webui.pages.base.BasePage
import io.github.flowboat.flowweather.webui.pages.root.PageFrame
import xyz.nulldev.kdom.api.Component
import xyz.nulldev.kdom.api.EmptyComponent
import xyz.nulldev.kdom.api.util.dataValue
import kotlin.browser.window

class ReportViewer(val parent: PageFrame, val report: DeviceReport) : BasePage("Report Viewer") {
    private val snapshot = field<Component>(EmptyComponent())
    private val sliderComp = field<Component>(EmptyComponent())

    //language=html
    override fun dom() = """
        <div>
        $snapshot
<div style="position: fixed; bottom: 0; left: 0; width: 100%; padding: 16px">
    $sliderComp
</div>
</div>
        """.toDom()

    override suspend fun onCompile() {
        val sensors = HttpApiProvider.getReportEntries(report)

        //Validate report size
        if(sensors.isEmpty()) {
            window.alert("This report is empty!")
            parent.goBack()
            return
        }

        //Create pages
        val pages = sensors.map {
            ReportEntryPage(it)
        }

        sliderComp.v = Component.from {
            val slider = htmlElement()

            onAttach = {
                slider().oninput = {
                    snapshot(pages[slider().dataValue.toInt()])
                }

                if(sensors.size <= 1)
                    slider().style.display = "none"
            }

            //language=html
            """
                <div style="width: calc(100% - 32px)">
    <mdc-slider kref="$slider" min="0" max="${sensors.size - 1}" initial="0"></mdc-slider>
    </div>
                """.toDom()
        }

        pages.firstOrNull()?.let { snapshot(it) }
    }
}
