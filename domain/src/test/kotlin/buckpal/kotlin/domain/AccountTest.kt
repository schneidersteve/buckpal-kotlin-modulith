/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package buckpal.kotlin.domain

import defaultAccount
import defaultActivity
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

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
        assertThat(balance).isEqualTo(Money.of(1555L))
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
        assertThat(success).isTrue
        assertThat(account.activityWindow.activities).hasSize(3)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L))
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
        assertThat(success).isFalse
        assertThat(account.activityWindow.activities).hasSize(2)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(1555L))
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
        assertThat(success).isTrue
        assertThat(account.activityWindow.activities).hasSize(3)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(2000L))
    }

}
