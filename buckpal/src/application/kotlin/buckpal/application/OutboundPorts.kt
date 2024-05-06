package buckpal.application

import buckpal.domain.ar.Account
import buckpal.domain.ar.AccountId
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime): Account
}

interface AccountLock {
    fun lockAccount(accountId: AccountId)
    fun releaseAccount(accountId: AccountId)
}

interface UpdateAccountStatePort {
    fun updateActivities(account: Account)
}
