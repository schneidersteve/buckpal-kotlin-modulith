package buckpal.kotlin.application

import buckpal.kotlin.domain.Account
import buckpal.kotlin.domain.AccountId
import java.time.LocalDateTime

interface LoadAccountPort {
    suspend fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime): Account
}

interface AccountLock {
    fun lockAccount(accountId: AccountId)
    fun releaseAccount(accountId: AccountId)
}

interface UpdateAccountStatePort {
    suspend fun updateActivities(account: Account)
}
