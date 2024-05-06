package buckpal.outbound.adapter.persistence

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@R2dbcRepository(dialect = Dialect.H2)
interface AccountRepository : CrudRepository<AccountEntity, Long>

@MappedEntity
data class AccountEntity(
    @GeneratedValue
    @field:Id
    val id: Long?,
)
