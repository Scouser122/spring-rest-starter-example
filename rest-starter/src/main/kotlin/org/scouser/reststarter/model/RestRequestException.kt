package org.scouser.reststarter.model

import org.springframework.http.HttpStatusCode
import java.lang.RuntimeException

class RestRequestException(val statusCode: HttpStatusCode, message: String) : RuntimeException(message)