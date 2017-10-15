package io.github.flowboat.flowweather.webui.pages.reportList

import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.webui.api.HttpApiProvider
import io.github.flowboat.flowweather.webui.pages.base.BasePage
import io.github.flowboat.flowweather.webui.pages.root.PageFrame
import xyz.nulldev.kdom.api.Component

class ReportList(val parent: PageFrame): BasePage("Reports") {
    val reportList = componentList<Component>()

    //language=html
    override fun dom() = """
        <nav class="mdc-list">
        $reportList
</nav>
        """.toDom()

    override suspend fun onAttach() {
        HttpApiProvider.getReports().forEach {
            reportList += report(it)
        }
    }

    fun report(report: DeviceReport) = Component.from {
        val root = htmlElement()

        onCompile = {
            root().onclick = {
                parent.setCurrentContent {
                    ReportViewer(report)
                }
            }
        }

        //language=html
        """
            <span kref="$root" class="mdc-list-item" style="cursor: pointer">
            ${report.date}: ${report.dbId}
                </span>
            """.toDom()
    }
}