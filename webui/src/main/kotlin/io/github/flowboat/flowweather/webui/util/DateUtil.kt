package io.github.flowboat.flowweather.webui.util

import kotlin.js.Date

fun parseDate(date: String): Date = js("(function(d) {return new Date(d);})")(date)
