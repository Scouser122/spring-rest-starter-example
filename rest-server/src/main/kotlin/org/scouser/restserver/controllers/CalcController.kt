package org.scouser.restserver.controllers

import com.fasterxml.jackson.databind.JsonNode
import org.scouser.restserver.services.CalcService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class CalcController(
    private val calcService: CalcService
) {
    @PostMapping("/sum")
    fun getSum(@RequestBody body: JsonNode): ResponseEntity<JsonNode> {
        return ResponseEntity(calcService.getSum(body), HttpStatus.OK)
    }
}