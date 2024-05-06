package buckpal.domain.test

import buckpal.domain.ar.AccountId
import buckpal.domain.vo.ActivityWindow
import buckpal.domain.vo.Money
import buckpal.testdata.defaultActivity
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ActivityWindowTest {

    @Test
    fun calculatesStartTimestamp() {
        val window = ActivityWindow(
            defaultActivity().withTimestamp(startDate()).build(),
            defaultActivity().withTimestamp(inBetweenDate()).build(),
            defaultActivity().withTimestamp(endDate()).build()
        )
        assertEquals(startDate(), window.getStartTimestamp())
    }

    @Test
    fun calculatesEndTimestamp() {
        val window = ActivityWindow(
            defaultActivity().withTimestamp(startDate()).build(),
            defaultActivity().withTimestamp(inBetweenDate()).build(),
            defaultActivity().withTimestamp(endDate()).build()
        )
        assertEquals(endDate(), window.getEndTimestamp())
    }

    @Test
    fun calculatesBalance() {
        val account1 = AccountId(1L)
        val account2 = AccountId(2L)
        val window = ActivityWindow(
            defaultActivity()
                .withSourceAccount(account1)
                .withTargetAccount(account2)
                .withMoney(Money.of(999)).build(),
            defaultActivity()
                .withSourceAccount(account1)
                .withTargetAccount(account2)
                .withMoney(Money.of(1)).build(),
            defaultActivity()
                .withSourceAccount(account2)
                .withTargetAccount(account1)
                .withMoney(Money.of(500)).build()
        )
        assertEquals(Money.of(-500), window.calculateBalance(account1))
        assertEquals(Money.of(500), window.calculateBalance(account2))
    }

}

private fun startDate(): LocalDateTime {
    return LocalDateTime.of(2019, 8, 3, 0, 0)
}

private fun inBetweenDate(): LocalDateTime {
    return LocalDateTime.of(2019, 8, 4, 0, 0)
}

private fun endDate(): LocalDateTime {
    return LocalDateTime.of(2019, 8, 5, 0, 0)
}
