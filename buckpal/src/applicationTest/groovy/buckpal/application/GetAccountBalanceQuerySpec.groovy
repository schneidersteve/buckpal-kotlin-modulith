package buckpal.application

import buckpal.application.query.GetAccountBalanceQueryImpl
import buckpal.domain.ar.Account
import buckpal.domain.ar.AccountId
import buckpal.domain.vo.Money
import spock.lang.Specification

class GetAccountBalanceQuerySpec extends Specification {

    LoadAccountPort loadAccountPort = Mock()

    GetAccountBalanceQuery getAccountBalanceQuery = new GetAccountBalanceQueryImpl(loadAccountPort)

    def "Succeeds"() {
        given: "a account"
            Account account = Mock()
            var accountId = new AccountId(41L)
            loadAccountPort.loadAccount(accountId, _) >> account

        when: "balance is queried"
            var balance = getAccountBalanceQuery.getAccountBalance(accountId)

        then: "balance is 500"
            balance == Money.@Companion.of(500L)
        and: "account balance is 500"
            1 * account.calculateBalance() >> Money.@Companion.of(500L)
    }
}
