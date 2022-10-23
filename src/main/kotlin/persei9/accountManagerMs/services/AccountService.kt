package persei9.accountManagerMs.services

import org.springframework.stereotype.Service
import persei9.accountManagerMs.models.Account
import persei9.accountManagerMs.repositories.AccountRepository
import java.util.UUID

@Service
class AccountService(private val accountRepository: AccountRepository) {
    fun save(account: Account) = accountRepository.save(account)
    fun listAll(accountId: UUID) = accountRepository.listAll(accountId)
    fun findById(id: UUID, accountId: UUID) = accountRepository.findById(id, accountId)
    fun findByHexId(id: String, accountId: UUID) = accountRepository.findByHexId(id, accountId)
}