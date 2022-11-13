/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package buckpal.kotlin.domain

import buckpal.kotlin.testdata.defaultAccount
import buckpal.kotlin.testdata.defaultActivity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AccountTest {

    @Test
    fun calculatesBalance() {
        val accountId = AccountId(1L)
        val account: Account =
            defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(555L))
                .withActivityWindow(
                    ActivityWindow(
                        defaultActivity()!!
                            .withTargetAccount(accountId)
                            .withMoney(Money.of(999L))
                            .build(),
                        defaultActivity()!!
                            .withTargetAccount(accountId)
                            .withMoney(Money.of(1L))
                            .build()
                    )
                )
                .build()
        val balance = account.calculateBalance()
        assertEquals(Money.of(1555L), balance)
    }

    @Test
    fun withdrawalSucceeds() {
        val accountId = AccountId(1L)
        val account = defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money.of(555L))
            .withActivityWindow(
                ActivityWindow(
                    defaultActivity()!!
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(999L)).build(),
                    defaultActivity()!!
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(1L)).build()
                )
            )
            .build()
        val success = account.withdraw(Money.of(555L), AccountId(99L))
        assert(success)
        assertEquals(3, account.activityWindow.activities.size)
        assertEquals(Money.of(1000L), account.calculateBalance())
    }

    @Test
    fun withdrawalFailure() {
        val accountId = AccountId(1L)
        val account = defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money.of(555L))
            .withActivityWindow(
                ActivityWindow(
                    defaultActivity()!!
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(999L)).build(),
                    defaultActivity()!!
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(1L)).build()
                )
            )
            .build()
        val success = account.withdraw(Money.of(1556L), AccountId(99L))
        assertFalse(success)
        assertEquals(2, account.activityWindow.activities.size)
        assertEquals(Money.of(1555L), account.calculateBalance())
    }

    @Test
    fun depositSuccess() {
        val accountId = AccountId(1L)
        val account = defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money.of(555L))
            .withActivityWindow(
                ActivityWindow(
                    defaultActivity()!!
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(999L)).build(),
                    defaultActivity()!!
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(1L)).build()
                )
            )
            .build()
        val success = account.deposit(Money.of(445L), AccountId(99L))
        assert(success)
        assertEquals(3, account.activityWindow.activities.size)
        assertEquals(Money.of(2000L), account.calculateBalance())
    }

}
