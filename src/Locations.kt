@file: UseExperimental(KtorExperimentalLocationsAPI::class)
package com.hadihariri

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@Location("/") class index()
@Location("/employee/{id}") class employee(val id: String)


fun Application.locations() {
    install(Locations)
    routing {
        get<index> {
            call.respondText("Routing Demo")
        }
        get<employee> { employee ->
            call.respondText(employee.id)

        }
    }
}