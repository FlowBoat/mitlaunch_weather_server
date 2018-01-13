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
        reportList.clear()
        HttpApiProvider.getReports().forEach {
            reportList += report(it)
        }
    }

    fun report(report: DeviceReport) = Component.from {
        val root = htmlElement()

        onCompile = {
            root().onclick = {
                parent.setCurrentContent(ReportViewer(parent, report))
            }
        }

        //language=html
        """
<span kref="$root" class="mdc-list-item" style="cursor: pointer">
    User: ${report.userId}, device: ${report.deviceId}, date: ${report.uploadDate}, id: ${report.dbId}
</span>
            """.toDom()
    }
}