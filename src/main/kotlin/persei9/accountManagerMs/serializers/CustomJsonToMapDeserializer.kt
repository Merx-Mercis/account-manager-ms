package persei9.accountManagerMs.serializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.IOException
import org.springframework.context.annotation.Configuration

@Configuration
class CustomJsonToMapDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Map<String, Any?>?>(vc) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): Map<String, Any?>? {
        val jsonNode = jp.codec.readTree(jp) as JsonNode

        return mapJsonStringToMap(jsonNode.textValue() ?: jacksonObjectMapper().writeValueAsString(jsonNode))
    }

    private fun mapJsonStringToMap(value: String): Map<String, Any?>? {
        return jacksonObjectMapper().readValue(value, object : TypeReference<Map<String, Any?>>() {})
    }
}