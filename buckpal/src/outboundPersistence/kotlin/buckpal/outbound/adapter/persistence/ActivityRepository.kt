package buckpal.outbound.adapter.persistence

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime

@R2dbcRepository(dialect = Dialect.H2)
interface ActivityRepository : CrudRepository<ActivityEntity, Long> {

    fun findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(
        ownerAccountId: Long,
        timestamp: LocalDateTime,
    ): List<ActivityEntity>

    @Query(
        """
        SELECT SUM(amount) FROM activity_entity
               WHERE target_account_id = :accountId
               AND owner_account_id = :accountId
               AND timestamp < :until
        """
    )
    fun getDepositBalanceUntil(accountId: Long, until: LocalDateTime): Long?

    @Query(
        "SELECT SUM(amount) FROM activity_entity " +
                "WHERE source_account_id = :accountId " +
                "AND owner_account_id = :accountId " +
                "AND timestamp < :until"
    )
    fun getWithdrawalBalanceUntil(accountId: Long, until: LocalDateTime): Long?

}

@MappedEntity
data class ActivityEntity(
    @GeneratedValue
    @field:Id
    val id: Long?,
    val timestamp: LocalDateTime,
    val ownerAccountId: Long,
    val sourceAccountId: Long,
    val targetAccountId: Long,
    val amount: Long,
)
