package io.github.flowboat.flowweather.webui.util

import kotlin.js.Date

fun parseDate(date: String): Date = js("new Date(date)")
fun parseDate(date: Double): Date = js("new Date(date)")
fun parseDate(num: Long): Date = parseDate(num.toDouble())
