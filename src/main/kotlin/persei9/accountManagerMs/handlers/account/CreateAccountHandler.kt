package persei9.accountManagerMs.handlers.account

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import persei9.accountManagerMs.dtos.domain.AccountDto
import persei9.accountManagerMs.dtos.services.CommandRequestDto
import persei9.accountManagerMs.constants.AccountConstants.DEFAULT_ACCOUNT_STATE
import persei9.accountManagerMs.exceptions.BadRequestException
import persei9.accountManagerMs.exceptions.ConflictException
import persei9.accountManagerMs.exceptions.ServiceException
import persei9.accountManagerMs.handlers.IDomainCommandHandler
import persei9.accountManagerMs.lib.AccountCommandLibrary.CREATE_ACCOUNT_COMMAND
import persei9.accountManagerMs.mappers.toModel
import persei9.accountManagerMs.services.AccountService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import persei9.accountManagerMs.dtos.services.account.CreateAccountDto
import java.util.UUID

@Component(CREATE_ACCOUNT_COMMAND)
class CreateAccountHandler(val accountService: AccountService): IDomainCommandHandler {
    @Transactional
    @Throws(BadRequestException::class, ConflictException::class, ServiceException::class)
    override fun handle(command: CommandRequestDto): Any {
        // val account: Account
        val params: CreateAccountDto

        try {
            params = jacksonObjectMapper().readValue(
                jacksonObjectMapper().writeValueAsString(command.params), CreateAccountDto::class.java
            )
        } catch (e: MissingKotlinParameterException) {
            throw BadRequestException(e.message)
        }

        val id = UUID.randomUUID()
        val companyId = UUID.fromString("23dfb14b-9252-430b-9df8-9f965cd96142")
        val createdBy = UUID.fromString("cf244be2-360d-4e00-af1f-692509f11687")

        params.let {
            try {
                accountService.save(
                    AccountDto(
                        id = id,
                        hexId = it.hexId,
                        serial = 1,
                        email = it.email,
                        name = it.name,
                        avatar = it.avatar,
                        background = it.background,
                        phoneNumbers = it.phoneNumbers,
                        title = it.title,
                        address = it.address,
                        birthday = it.birthday,
                        notes = it.notes,
                        tags = it.tags,
                        state = DEFAULT_ACCOUNT_STATE,
                        companyId = companyId,
                        createdBy = createdBy,
                    ).toModel()
                )
            } catch (e: ConflictException) {
                throw ConflictException(e.message)
            } catch (e: ServiceException) {
                throw ServiceException(e.message)
            }
        }

        return mapOf(
            "account" to accountService.findById(id, companyId)
        )
    }
}