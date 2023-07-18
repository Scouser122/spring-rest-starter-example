package org.scouser.restserver.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class CalcService {
    fun getSum(data: JsonNode): JsonNode {
        val x = data.get("x").asDouble()
        val y = data.get("y").asDouble()
        val result = x + y
        val resultNode = ObjectMapper().createObjectNode()
        resultNode.put("result", result)
        return resultNode
    }
}