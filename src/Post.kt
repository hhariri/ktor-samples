
package com.hadihariri

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

fun Application.post() {
    routing {
        get("/") {
            val contentType = ContentType.Text.Html.withCharset(Charsets.UTF_8)

            call.respondHtml {
                head {
                    title { +"POST" }
                    meta {
                        httpEquiv = HttpHeaders.ContentType
                        content = contentType.toString()
                    }
                }
                body {
                    p {
                        +"File upload example"
                    }
                    form(action = "form", encType = FormEncType.multipartFormData, method = FormMethod.post) {
                        acceptCharset = "utf-8"
                        textInput { name = "field1" }
                        br
                        fileInput { name = "file1" }
                        br
                        submitInput { value = "send" }
                    }
                }
            }
        }

        post("/form") {
            val multipart = call.receiveMultipart()
            call.respondTextWriter {
                if (!call.request.isMultipart()) {
                    appendln("Not a multipart request")
                } else {
                    multipart.forEachPart { part ->
                        when (part) {
                            is PartData.FormItem -> appendln("Form field: $part = ${part.value}")
                            is PartData.FileItem -> appendln("File field: $part -> ${part.originalFileName} of ${part.contentType}")
                        }
                        part.dispose()
                    }
                }
            }

        }
    }
}