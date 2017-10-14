package io.github.flowboat.flowweather.server.http.api.reports.model

import kotlinx.serialization.Serializable

//TODO SHARED CLASS!
@Serializable
data class DeviceReport(val sensorMap: Map<SerializableSensor, SerializableSensorValues>)