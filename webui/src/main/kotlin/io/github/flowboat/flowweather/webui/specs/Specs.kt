package io.github.flowboat.flowweather.webui.specs

import xyz.nulldev.kdom.api.SpecManager

fun registerAllSpecs() {
    MDCSpecs.forEach(SpecManager::registerSpec)
}