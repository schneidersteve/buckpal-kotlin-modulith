package buckpal.application

import buckpal.domain.ar.AccountId
import buckpal.domain.vo.Money

// ------------------------------
// Command-Query Separation (CQS)
// ------------------------------

interface SendMoneyUseCase {
    suspend fun sendMoney(command: SendMoneyCommand): Boolean
}

// TODO implement reflection free validating
data class SendMoneyCommand(val sourceAccountId: AccountId, val targetAccountId: AccountId, val money: Money) {}

interface GetAccountBalanceQuery {
    suspend fun getAccountBalance(accountId: AccountId): Money
}
