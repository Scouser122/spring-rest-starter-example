package org.scouser.reststarter.handlers

import org.scouser.reststarter.model.RestRequestException
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import java.io.BufferedReader
import java.io.IOException
import java.util.*

class RestErrorHandler : ResponseErrorHandler {
    override fun hasError(response: ClientHttpResponse): Boolean {
        val status = response.statusCode
        return Objects.nonNull(status) && status.isError
    }

    override fun handleError(response: ClientHttpResponse) {
        val statusCode = response.statusCode
        val responseBody = response.body.bufferedReader().use(BufferedReader::readText)
        throw RestRequestException(statusCode, "rest request error. body: $responseBody")
    }
}