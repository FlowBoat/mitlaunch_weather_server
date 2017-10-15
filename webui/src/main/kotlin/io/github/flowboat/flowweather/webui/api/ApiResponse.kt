package io.github.flowboat.flowweather.webui.api

sealed class ApiResponse {
    class Success()
    class Failure(error: String)
}