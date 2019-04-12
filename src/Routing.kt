

package com.hadihariri

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.routingApp() {
    routing {
        get("/") {
            call.respondText("Routing Demo")
        }
        route("customer") {
            get {
                call.respondText(call.request.queryParameters["id"].toString())
            }
            post {
                call.respondText("Performed a POST")
            }
        }
        route("employee") {
            get("{id}") {
                call.respondText(call.parameters["id"].toString())
            }
        }
        route("shipment") {
            route("international") {
                get {
                    call.respondText("International Shipments")
                }
            }
            route("national") {
                get {
                    call.respondText("National Shipments")
                }
            }
        }
    }
}