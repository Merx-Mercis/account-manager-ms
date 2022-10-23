package persei9.accountManagerMs.mappers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import persei9.accountManagerMs.dtos.domain.AccountDto

import persei9.accountManagerMs.models.Account

fun AccountDto.toModel(): Account {
    return Account(
        id = this.id,
        hexId = this.hexId,
        serial = this.serial,
        email = this.name,
        token = this.token,
        name = this.name,
        avatar = this.avatar,
        background = this.background,
        phoneNumbers = this.phoneNumbers,
        title = this.title,
        address = this.address,
        birthday = this.birthday,
        notes = this.notes,
        tags = this.tags,
        state = this.state,
        companyId = this.companyId,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        updatedBy = this.updatedBy,
        updatedAt = this.updatedAt,
        deletedBy = this.deletedBy,
        deletedAt = this.deletedAt,
    )
}

fun List<AccountDto>.toModelList() = map { it.toModel() }.toList()

fun MutableList<MutableMap<String, Any?>>.toAccountList(objectMapper: ObjectMapper): List<Account> {
    return map { objectMapper.convertValue(it, object : TypeReference<Account>() {}) }
        .toList()
}