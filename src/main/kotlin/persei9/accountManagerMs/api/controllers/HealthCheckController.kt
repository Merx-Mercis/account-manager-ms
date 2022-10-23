package persei9.accountManagerMs.api.controllers

import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import persei9.accountManagerMs.api.routes.Router.HEALTH_CHECK_URL

@RestController
class HealthCheckController {
    @GetMapping(HEALTH_CHECK_URL)
    fun healthCheck() = ok(mapOf("status" to "ok"))
}