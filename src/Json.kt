
package com.hadihariri

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*

class Customer(val id: Int, val name: String, val email: String)

fun Application.json() {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
    routing {
        get("/customer") {
            val model = Customer(1, "Mary Jane", "mary@jane.com")
            call.respond(model)
        }
    }
}