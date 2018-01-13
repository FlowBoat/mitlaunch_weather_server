package io.github.flowboat.flowweather.webui.pages.reportList

import xyz.nulldev.kdom.api.Component

class SensorComponent(val sensor: String, val unit: String, val value: Double) : Component() {
    //language=html
    override fun dom() = """
<div>
    <h3 class="mdc-list-group__subheader">$sensor</h3>
    <ul class="mdc-list">
        <li class="mdc-list-item">$value $unit</li>
    </ul>
</div>
        """.toDom()
}