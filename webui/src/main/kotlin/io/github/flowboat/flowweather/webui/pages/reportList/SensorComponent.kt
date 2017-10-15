package io.github.flowboat.flowweather.webui.pages.reportList

import io.github.flowboat.flowweather.shared.sensor.Sensor
import io.github.flowboat.flowweather.shared.sensor.SensorValue
import io.github.flowboat.flowweather.webui.api.HttpApiProvider
import xyz.nulldev.kdom.api.Component

class SensorComponent(val sensor: Sensor) : Component() {
    private val sensorValueEntries = componentList<Component>()

    //language=html
    override fun dom() = """
<div>
    <h3 class="mdc-list-group__subheader">${sensor.vendor}: ${sensor.name}</h3>
    <ul class="mdc-list">
        $sensorValueEntries
    </ul>
</div>
        """.toDom()

    override suspend fun onAttach() {
        HttpApiProvider.getSensorValues(sensor).sortedBy { it.index }.forEach {
            sensorValueEntries += sensorValueEntry(it)
        }
    }

    fun sensorValueEntry(sensorValue: SensorValue) = Component.from {
        //language=html
        """
<li class="mdc-list-item">
    ${sensorValue.value}
</li>
            """.toDom()
    }
}