package persei9.accountManagerMs.managers

import persei9.accountManagerMs.dtos.services.CommandRequestDto
import persei9.accountManagerMs.exceptions.BadRequestException
import persei9.accountManagerMs.exceptions.CommandNotFoundException
import persei9.accountManagerMs.exceptions.ConflictException
import persei9.accountManagerMs.exceptions.ServiceException
import persei9.accountManagerMs.handlers.IDomainCommandHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import persei9.accountManagerMs.models.Account
import persei9.accountManagerMs.services.AccountService
import java.util.*

@Component
class AccountDomainManager(private val applicationContext: ApplicationContext) {
    @Autowired
    private val accountService: AccountService? = null
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(
        ServiceException::class,
        CommandNotFoundException::class,
        ConflictException::class,
        BadRequestException::class
    )
    fun runSync(command: CommandRequestDto): Any {
        logger.debug("received message for ${command.name}")

        try {
            val handler = command.name.let {
                applicationContext.getBean(it) as IDomainCommandHandler
            }

            return handler.handle(command)
        } catch (e: NoSuchBeanDefinitionException) {
            val message = "no command manager found for command ${command.name}"

            logger.warn(message)

            throw CommandNotFoundException(message)
        } catch (e: ConflictException) {
            throw ConflictException(e.message)
        } catch (e: BadRequestException) {
            throw BadRequestException(e.localizedMessage)
        } catch (e: ServiceException) {
            logger.error(e.message)

            throw ServiceException(e.message)
        }

    }

    @Throws(
        ServiceException::class,
    )
    fun getAccounts(): Any {
        logger.debug("get accounts")
        val companyId = UUID.fromString("23dfb14b-9252-430b-9df8-9f965cd96142")

        try {
            return accountService!!.listAll(companyId)
        } catch (e: ServiceException) {
            logger.error(e.message)

            throw ServiceException(e.message)
        }
    }

    @Throws(
        ServiceException::class,
    )
    fun getAccount(id: UUID): Account? {
        logger.debug("get account $id")
        val companyId = UUID.fromString("23dfb14b-9252-430b-9df8-9f965cd96142")

        try {
            return accountService!!.findById(id, companyId)
        } catch (e: ServiceException) {
            logger.error(e.message)

            throw ServiceException(e.message)
        }
    }

    @Throws(
        ServiceException::class,
    )
    fun getAccountByHexId(hexId: String): Account? {
        logger.debug("get account by hexId: $hexId")
        val companyId = UUID.fromString("23dfb14b-9252-430b-9df8-9f965cd96142")

        try {
            return accountService!!.findByHexId(hexId, companyId)
        } catch (e: ServiceException) {
            logger.error(e.message)

            throw ServiceException(e.message)
        }
    }
}