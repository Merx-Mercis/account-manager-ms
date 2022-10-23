package persei9.accountManagerMs.repositories.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Component

@Component
abstract class BaseRepository(
    val tableName: String = "",
    val keyName: String = "id"
) {

    @Autowired
    protected lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    protected fun getJdbcTemplate() = namedParameterJdbcTemplate.jdbcTemplate

    protected val jdbcInsert: SimpleJdbcInsert
        get() = SimpleJdbcInsert(getJdbcTemplate()).withTableName(tableName)

    fun batchInsert(rows: List<BeanPropertySqlParameterSource>, setGeneratedKeyName: Boolean = true): Int {
        return jdbcInsert
            .also {
                if (setGeneratedKeyName) it.setGeneratedKeyName(keyName)
            }
            .executeBatch(*rows.toTypedArray())
            .size
    }

    fun <T> insert(row: T, setGeneratedKeyName: Boolean = true): Long {
        return jdbcInsert
            .also {
                if (setGeneratedKeyName) it.setGeneratedKeyName(keyName)
            }
            .executeAndReturnKey(BeanPropertySqlParameterSource(row as Any))
            .toLong()
    }

    fun <T> insertKeyNoGenerated(row: T) =
        jdbcInsert
            .execute(BeanPropertySqlParameterSource(row as Any))
}