package com.example.smart.presentation

import fi.iki.elonen.NanoHTTPD

class HTTPServer(private val getHeartRate: () -> String) : NanoHTTPD(8080) {

    override fun serve(session: IHTTPSession): Response {
        return when (session.uri) {
            "/heartrate" -> {
                val heartRate = getHeartRate()
                newFixedLengthResponse(Response.Status.OK, "text/plain", heartRate)
            }
            else -> {
                newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Resource not found")
            }
        }
    }
}
