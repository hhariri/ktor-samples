package com.hadihariri

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.home() {
    get("/") {
        call.respondText("Index Page")
    }
    get("/about") {
        call.respondText("About Page")
    }
}


fun Route.employees() {
    route("/employee") {
        get {
            call.respondText("Employee Page")
        }
        post {

        }
        delete {

        }
    }
}

fun Application.appStructure() {
    routing {
        home()
        employees()
    }

}



