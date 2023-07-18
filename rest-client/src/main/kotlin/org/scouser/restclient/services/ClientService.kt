package org.scouser.restclient.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@Service
class ClientService(
    private val restTemplate: RestTemplate
) {
    fun getSumFromServer(x: Int, y: Int): JsonNode {
        val requestBody = ObjectMapper().createObjectNode()
        requestBody.put("x", x)
        requestBody.put("y", y)
        val response = try {
            restTemplate.postForEntity("http://localhost:9090/sum", requestBody, JsonNode::class.java)
        } catch (ex: Exception) {
            return ObjectMapper().createObjectNode().put("error", ex.message)
        }
        return response.body ?: ObjectMapper().createObjectNode()
    }
}