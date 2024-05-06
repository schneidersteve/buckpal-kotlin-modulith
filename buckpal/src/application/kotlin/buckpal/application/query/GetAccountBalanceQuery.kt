package buckpal.application.query

import buckpal.application.GetAccountBalanceQuery
import buckpal.application.LoadAccountPort
import buckpal.domain.ar.AccountId
import buckpal.domain.vo.Money
import jakarta.inject.Singleton
import java.time.LocalDateTime

@Singleton
class GetAccountBalanceQueryImpl(
    private val loadAccountPort: LoadAccountPort,
) : GetAccountBalanceQuery {
    override fun getAccountBalance(accountId: AccountId): Money {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance()
    }
}
