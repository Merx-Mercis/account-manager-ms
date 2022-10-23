package persei9.accountManagerMs.api.controllers

import persei9.accountManagerMs.annotations.ApiKey
import persei9.accountManagerMs.dtos.services.CommandRequestDto
import persei9.accountManagerMs.api.routes.Router
import persei9.accountManagerMs.exceptions.*
import persei9.accountManagerMs.managers.AccountDomainManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(Router.ACCOUNTS_BASE_URL)
class AccountsController {
    @Autowired
    val accountDomainManager: AccountDomainManager? = null

    @ApiKey
    @PostMapping(Router.ACCOUNTS_COMMAND_URL)
    fun runSyncCommand(@RequestBody command: CommandRequestDto): ResponseEntity<Any> {
        return try {
            val result = accountDomainManager!!.runSync(command)

            val response = mapOf(
                "response" to mapOf(
                    "data" to result,
                    "code" to HttpStatus.OK.value(),
                    "message" to "success"
                )
            )

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: ServiceException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message" to "server error"
                )
            )

            ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: CommandNotFoundException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.NOT_FOUND.value(),
                    "message" to e.message
                )
            )

            ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
        } catch (e: NotFoundException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.NOT_FOUND.value(),
                    "message" to e.message
                )
            )

            ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
        } catch (e: ConflictException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.CONFLICT.value(),
                    "message" to e.message
                )
            )

            ResponseEntity(errorResponse, HttpStatus.CONFLICT)
        } catch (e: BadRequestException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.BAD_REQUEST.value(),
                    "message" to e.message
                )
            )

            ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
        }
    }

    @ApiKey
    @GetMapping
    fun getAccounts(): ResponseEntity<Any> {
        return try {
            val result = accountDomainManager!!.getAccounts()

            val response = mapOf(
                "response" to mapOf(
                    "data" to result,
                    "code" to HttpStatus.OK.value(),
                    "message" to "success"
                )
            )

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: ServiceException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message" to "server error"
                )
            )

            ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @ApiKey
    @GetMapping("/{hexId}")
    fun getAccount(@PathVariable("hexId") hexId: String): ResponseEntity<Any> {
        return try {
            val result = accountDomainManager!!.getAccountByHexId(hexId)

            val response = mapOf(
                "response" to mapOf(
                    "data" to result,
                    "code" to HttpStatus.OK.value(),
                    "message" to "success"
                )
            )

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: ServiceException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message" to "server error"
                )
            )

            ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to HttpStatus.NOT_FOUND.value(),
                    "message" to e.message
                )
            )

            ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
        }
    }
}