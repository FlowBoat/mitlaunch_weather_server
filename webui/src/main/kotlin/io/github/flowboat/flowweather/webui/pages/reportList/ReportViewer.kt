package io.github.flowboat.flowweather.webui.pages.reportList

import io.github.flowboat.flowweather.shared.sensor.DeviceReport
import io.github.flowboat.flowweather.shared.sensor.Sensor
import io.github.flowboat.flowweather.webui.api.HttpApiProvider
import io.github.flowboat.flowweather.webui.pages.base.BasePage
import io.github.flowboat.flowweather.webui.util.parseDate
import xyz.nulldev.kdom.api.Component
import xyz.nulldev.kdom.api.EmptyComponent
import xyz.nulldev.kdom.api.util.dataValue
import kotlin.js.Date

class ReportViewer(val report: DeviceReport) : BasePage("Report Viewer") {
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
        val sensorEventChain = mutableListOf<SensorSnapshot>()

        val sensors = HttpApiProvider.getSensors(report)
        val groups = HttpApiProvider.getSensorDataGroups(report).map {
            Pair(it, parseDate(it.jsTime))
        }

        //Place groups in chain (but do not include values as we don't have them yet)
        groups.forEach {
            sensorEventChain.add(SensorSnapshot(it.second))
        }

        //Create pages
        val pages = sensorEventChain.map {
            SensorPage(it)
        }

        sliderComp.v = Component.from {
            val slider = htmlElement()

            onAttach = {
                slider().oninput = {
                    snapshot(pages[slider().dataValue.toInt()])
                }
            }

            //language=html
            """
                <div style="width: calc(100% - 32px)">
    <mdc-slider kref="$slider" min="0" max="${groups.size - 1}" initial="0"></mdc-slider>
    </div>
                """.toDom()
        }

        pages.firstOrNull()?.let { snapshot(it) }

        //Load group values
        groups.forEach { group ->
            val values = HttpApiProvider.getSensorValues(group.first)

            val snapshot = sensorEventChain.find { it.time == group.second }
                ?: throw IllegalStateException("Snapshot @ ${group.second} is missing!")

            snapshot.content = values.groupBy { it.sensorId }.map { (sensorId, v) ->
                val sensor = sensors.find { it.dbId == sensorId }
                    ?: throw IllegalStateException("Sensor $sensorId is missing!")

                Pair(sensor, v.map { it.value.toFloat() }.toFloatArray())
            }
        }
    }
}

data class SensorSnapshot(var time: Date) {
    var content: List<Pair<Sensor, FloatArray>>? = null
    set(value) {
        field = value
        updateListener?.let { it() }
    }

    var updateListener: (() -> Unit)? = null
}
