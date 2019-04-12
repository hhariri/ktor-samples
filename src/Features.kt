
package com.hadihariri

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*



fun Application.features() {
    install(DefaultHeaders)
    install(Compression)

    routing {
        get("/headers") {
            call.respondText("Check the headers", ContentType.Text.Plain)
        }

    }
}