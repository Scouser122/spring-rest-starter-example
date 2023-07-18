package org.scouser.reststarter.beans

import net.logstash.logback.argument.StructuredArguments.kv
import org.scouser.reststarter.model.RestRequestException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.BufferedReader
import java.nio.charset.StandardCharsets
import java.util.*

class RequestInterceptor : ClientHttpRequestInterceptor {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {

        val method: String? = if (Objects.nonNull(request.method)) request.method.name() else null

        logger.info(
            "send rest request {}, {}, {}, {}",
            kv("method", method),
            kv("uri", request.uri),
            kv("headers", request.headers),
            kv("body", String(body, StandardCharsets.UTF_8))
        )

        val response = try {
            execution.execute(request, body)
        } catch (e: Exception) {
            logger.error("error executing request", e)
            throw RestRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: e.toString())
        }

        val responseBody = response.body.bufferedReader().use(BufferedReader::readText)

        logger.info(
            "got rest response, {}, {}, {}, {}, {}",
            kv("method", method),
            kv("uri", request.uri),
            kv("headers", response.headers),
            kv("code", response.statusCode.value()),
            kv("body", responseBody)
        )

        return response
    }
}