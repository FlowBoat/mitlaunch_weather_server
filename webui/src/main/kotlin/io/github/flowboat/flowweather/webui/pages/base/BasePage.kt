package io.github.flowboat.flowweather.webui.pages.base

import xyz.nulldev.kdom.api.Component

abstract class BasePage(val name: String): Component()

typealias PageGenerator = () -> BasePage