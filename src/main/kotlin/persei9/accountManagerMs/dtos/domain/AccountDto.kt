package persei9.accountManagerMs.dtos.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import persei9.accountManagerMs.serializers.CustomJsonArrayToMapListDeserializer
import java.util.*

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AccountDto(
    val id: UUID? = UUID.randomUUID(),
    val hexId: String,
    var serial: Long,
    var email: String,
    var token: String? = null,
    var name: String,
    var avatar: String? = null,
    var background: String? = null,
    @JsonDeserialize(using = CustomJsonArrayToMapListDeserializer::class)
    var phoneNumbers: List<Map<String, Any?>>? = emptyList(),
    var title: String? = null,
    var address: String? = null,
    var birthday: Date? = null,
    var notes: String? = null,
    @JsonDeserialize(using = CustomJsonArrayToMapListDeserializer::class)
    var tags: List<Map<String, Any?>>? = emptyList(),
    var state: String,
    var companyId: UUID,
    var createdBy: UUID,
    var createdAt: Date = Date(),
    var updatedBy: UUID? = null,
    var updatedAt: Date? = null,
    var deletedBy: UUID? = null,
    var deletedAt: Date? = null,
)