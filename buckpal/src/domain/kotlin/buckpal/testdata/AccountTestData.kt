package buckpal.testdata

import buckpal.domain.ar.Account
import buckpal.domain.ar.AccountId
import buckpal.domain.vo.ActivityWindow
import buckpal.domain.vo.Money

fun defaultAccount(): AccountBuilder {
    return AccountBuilder()
        .withAccountId(AccountId(42L))
        .withBaselineBalance(Money.of(999L))
        .withActivityWindow(
            ActivityWindow(
                defaultActivity()!!.build(), defaultActivity()!!.build()
            )
        )
}

class AccountBuilder {
    private var accountId: AccountId? = null
    private var baselineBalance: Money? = null
    private var activityWindow: ActivityWindow? = null

    fun withAccountId(accountId: AccountId): AccountBuilder {
        this.accountId = accountId
        return this
    }

    fun withBaselineBalance(baselineBalance: Money): AccountBuilder {
        this.baselineBalance = baselineBalance
        return this
    }

    fun withActivityWindow(activityWindow: ActivityWindow): AccountBuilder {
        this.activityWindow = activityWindow
        return this
    }

    fun build(): Account {
        return Account.withId(accountId!!, baselineBalance!!, activityWindow!!)
    }
}
