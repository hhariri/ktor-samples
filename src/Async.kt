package com.hadihariri

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.coroutines.*
import kotlinx.html.*
import java.util.*
import kotlin.system.*

typealias DelayProvider = suspend (ms: Int) -> Unit

val compute = newFixedThreadPoolContext(4, "compute")


fun Application.async(random: Random = Random(), delayProvider: DelayProvider = { delay(it.toLong()) }) {
    install(DefaultHeaders)
    install(CallLogging)
    routing {
        // Tabbed browsers can wait for first request to complete in one tab before making a request in another tab.
        // Presumably they assume second request will hit 304 Not Modified and save on data transfer.
        // If you want to verify simultaneous connections, either use "curl" or use different URLs in different tabs
        // Like localhost:8080/1, localhost:8080/2, localhost:8080/3, etc
        get("/{...}") {
            val startTime = System.currentTimeMillis()
            call.respondHandlingLongCalculation(random, delayProvider, startTime)
        }
    }
}

private suspend fun ApplicationCall.respondHandlingLongCalculation(random: Random, delayProvider: DelayProvider, startTime: Long) {
    val queueTime = System.currentTimeMillis() - startTime
    var number = 0
    val computeTime = measureTimeMillis {
        // We specify a coroutine context, that will use a thread pool for long computing operations.
        // In this case it is not necessary since we are "delaying", not sleeping the thread.
        // But serves as an example of what to do if we want to perform slow non-asynchronous operations
        // that would block threads.
        withContext(compute) {
            for (index in 0 until 300) {
                delayProvider(10)
                number += random.nextInt(100)
            }
        }
    }

    respondHtml {
        head {
            title { +"Ktor: async" }
        }
        body {
            p {
                +"Hello from Ktor Async sample application"
            }
            p {
                +"We calculated number $number in $computeTime ms of compute time, spending $queueTime ms in queue."
            }
        }
    }
}
