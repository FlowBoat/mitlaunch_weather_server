package io.github.flowboat.flowweather.webui.pages.reportList

import xyz.nulldev.kdom.api.Component

class SensorPage(val snapshot: SensorSnapshot): Component() {
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
        snapshot.updateListener = {
            update()
        }
        update()
    }

    private fun update() {
        snapshot.content?.apply {
            loaderVisibility("none")
            entries.clear()
            forEach {
                entries += SensorComponent(it.first, it.second)
            }
        } ?: run {
            loaderVisibility("block")
        }
    }
}