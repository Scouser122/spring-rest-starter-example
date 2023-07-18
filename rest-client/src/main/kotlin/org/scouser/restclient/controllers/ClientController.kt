package org.scouser.restclient.controllers

import com.fasterxml.jackson.databind.JsonNode
import org.scouser.restclient.services.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ClientController(
    private val service: ClientService
) {
    @GetMapping("/sum")
    fun getData(@RequestParam x: Int, @RequestParam y: Int): ResponseEntity<JsonNode> {
        val data = service.getSumFromServer(x, y)
        return ResponseEntity(data, HttpStatus.OK)
    }
}