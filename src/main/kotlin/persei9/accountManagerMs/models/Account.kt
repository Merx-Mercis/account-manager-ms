package persei9.accountManagerMs.models

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import persei9.accountManagerMs.repositories.TableNames.ACCOUNTS
import persei9.accountManagerMs.serializers.CustomJsonArrayToMapListDeserializer
import java.util.Date
import java.util.UUID

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Account(
    var id: UUID? = UUID.randomUUID(),
    val hexId: String,
    val serial: Long,
    val email: String,
    val token: String? = null,
    val name: String,
    val avatar: String? = null,
    val background: String? = null,
    @JsonDeserialize(using = CustomJsonArrayToMapListDeserializer::class)
    val phoneNumbers: List<Map<String, Any?>>? = emptyList(),
    val title: String? = null,
    val address: String? = null,
    val birthday: Date? = null,
    val notes: String? = null,
    @JsonDeserialize(using = CustomJsonArrayToMapListDeserializer::class)
    val tags: List<Map<String, Any?>>? = emptyList(),
    val state: String,
    val companyId: UUID,
    val createdBy: UUID,
    val createdAt: Date = Date(),
    val updatedBy: UUID? = null,
    val updatedAt: Date? = null,
    val deletedBy: UUID? = null,
    val deletedAt: Date? = null,
) {
    companion object {
        const val TABLE_NAME = ACCOUNTS
    }
}