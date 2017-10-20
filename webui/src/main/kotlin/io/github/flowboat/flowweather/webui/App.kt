package io.github.flowboat.flowweather.webui

import io.github.flowboat.flowweather.webui.pages.root.PageFrame
import io.github.flowboat.flowweather.webui.specs.registerAllSpecs
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.onload = {
        //Register specs
        registerAllSpecs()

        val page = PageFrame()
        document.body!!.appendChild(page.compiledDom.root)
        page.checkAttached()
    }
}
