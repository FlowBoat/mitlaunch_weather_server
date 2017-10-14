package io.github.flowboat.flowweather.util

fun Double.finiteOrNegativeOne()
        = if(isFinite())
    this
else -1.0
