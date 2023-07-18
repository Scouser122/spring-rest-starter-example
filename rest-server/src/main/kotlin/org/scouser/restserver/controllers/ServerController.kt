package org.scouser.restserver.controllers

import com.fasterxml.jackson.databind.JsonNode
import org.scouser.restserver.services.ServerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class ServerController(
    private val serverService: ServerService
) {
    @PostMapping("/sum")
    fun getData(@RequestBody body: JsonNode): ResponseEntity<JsonNode> {
        return ResponseEntity(serverService.getSum(body), HttpStatus.OK)
    }
}