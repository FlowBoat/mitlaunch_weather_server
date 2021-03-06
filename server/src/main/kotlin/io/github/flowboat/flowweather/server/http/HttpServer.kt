package io.github.flowboat.flowweather.server.http

import io.github.flowboat.flowweather.server.http.api.app.reports.SubmitReportRoute
import io.github.flowboat.flowweather.server.http.api.webui.reports.ListReportsRoute
import io.github.flowboat.flowweather.server.http.api.webui.reports.ListReportEntriesRoute
import org.jetbrains.ktor.content.resources
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.post
import org.jetbrains.ktor.routing.route
import org.jetbrains.ktor.routing.routing

class HttpServer {
    fun boot() {
        val server = embeddedServer(Netty, 9999) {
            routing {
                static("/" ) {
                    resources("/webui")
                }
                route("/api") {
                    route("/v1") {
                        route("/app") {
                            post("/submit-report", SubmitReportRoute().handler)
                        }
                        route("/webui") {
                            get("/list-reports", ListReportsRoute().handler)
                            get("/list-report-entries", ListReportEntriesRoute().handler)
                        }
                    }
                }
            }
        }
        server.start(wait = true)
    }
}