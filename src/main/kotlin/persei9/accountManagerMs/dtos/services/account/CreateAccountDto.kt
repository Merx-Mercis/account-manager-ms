package persei9.accountManagerMs.dtos.services.account

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import persei9.accountManagerMs.serializers.CustomJsonArrayToMapListDeserializer
import java.util.Date

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateAccountDto(
    val hexId: String,
    val email: String,
    val name: String,
    val avatar: String? = null,
    val background: String? = null,
    @JsonDeserialize(using = CustomJsonArrayToMapListDeserializer::class)
    var phoneNumbers: List<Map<String, Any?>>? = emptyList(),
    val title: String? = null,
    val address: String? = null,
    val birthday: Date? = null,
    val notes: String? = null,
    @JsonDeserialize(using = CustomJsonArrayToMapListDeserializer::class)
    val tags: List<Map<String, Any?>>? = emptyList(),
)