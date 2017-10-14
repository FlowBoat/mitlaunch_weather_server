package io.github.flowboat.flowweather.server.http.api.reports.model

import kotlinx.serialization.Serializable
import java.util.*

//TODO SHARED CLASS!
@Serializable
data class SerializableSensorValues(val value: List<Float>)
