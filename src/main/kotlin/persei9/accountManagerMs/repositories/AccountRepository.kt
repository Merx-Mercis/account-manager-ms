package persei9.accountManagerMs.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import persei9.accountManagerMs.mappers.toAccountList
import persei9.accountManagerMs.models.Account
import persei9.accountManagerMs.repositories.impl.BaseRepository
import persei9.accountManagerMs.repositories.queryBuilders.AccountQueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.stereotype.Repository
import persei9.accountManagerMs.exceptions.ConflictException
import persei9.accountManagerMs.exceptions.ServiceException
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.sql.Types
import java.util.UUID

@Repository
class AccountRepository: BaseRepository(tableName = Account.TABLE_NAME) {
    @Autowired
    lateinit var objectMapper: ObjectMapper
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun saveAll(accounts: List<Account>) {
        getJdbcTemplate().batchUpdate(AccountQueryBuilder.INSERT, object : BatchPreparedStatementSetter {
            @Override
            override fun setValues(ps: PreparedStatement, i: Int) {
                val account = accounts[i]
                ps.setString(1, account.id.toString())
                ps.setString(2, account.hexId)
                ps.setLong(3, account.serial)
                ps.setString(4, account.email)
                ps.setString(5, account.name)
                ps.setString(6, account.avatar)
                ps.setString(7, account.background)
                ps.setString(8, jacksonObjectMapper().writeValueAsString(account.phoneNumbers))
                ps.setString(9, account.title)
                ps.setString(10, account.address)
                ps.setString(11, account.birthday?.toString())
                ps.setString(12, account.notes)
                ps.setString(13, jacksonObjectMapper().writeValueAsString(account.tags))
                ps.setString(14, account.state)
                ps.setString(15, account.companyId.toString())
                ps.setString(16, account.createdBy.toString())
                ps.setTimestamp(17, Timestamp.from(account.createdAt.toInstant()))
            }

            @Override
            override fun getBatchSize() = accounts.size
        })
    }

    fun save(account: Account) {
        return try {
            saveAll(listOf(account))
        } catch (e: DataAccessException) {
            if (e.message.toString().contains("ERROR: duplicate key", ignoreCase = true)) {
                    throw ConflictException("already exists an user with this hex id")
            } else {
                logger.error(e.message)
                throw ServiceException("DATABASE_ERROR(database error)")
            }
        }
    }

    fun findById(id: UUID, accountId: UUID) = getJdbcTemplate()
        .queryForList(
            AccountQueryBuilder.FIND_BY_ID,
            arrayOf(id.toString(), accountId.toString()),
            intArrayOf(Types.OTHER, Types.OTHER))
        .toAccountList(objectMapper)
        .firstOrNull()

    fun findByHexId(hexId: String, accountId: UUID) = getJdbcTemplate()
        .queryForList(
            AccountQueryBuilder.FIND_BY_HEX_ID,
            arrayOf(hexId, accountId.toString()),
            intArrayOf(Types.OTHER, Types.OTHER))
        .toAccountList(objectMapper)
        .firstOrNull()

    fun listAll(accountId: UUID): List<Account> = getJdbcTemplate()
        .queryForList(
            AccountQueryBuilder.LIST_ALL,
            arrayOf(accountId.toString()),
            intArrayOf(Types.OTHER))
        .toAccountList(objectMapper)

}