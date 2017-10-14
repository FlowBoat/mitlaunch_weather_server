package io.github.flowboat.flowweather.server.http.api.reports.model

import kotlinx.serialization.Serializable

//TODO SHARED CLASS!
@Serializable
data class SerializableSensor(val name: String,
                              val vendor: String,
                              val power: Float)
