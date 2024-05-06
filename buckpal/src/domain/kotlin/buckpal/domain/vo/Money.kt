package buckpal.domain.vo

import java.math.BigInteger

@JvmRecord
data class Money(val amount: BigInteger) {

    companion object {
        val ZERO: Money = of(0L)

        // Functions

        fun of(value: Long): Money {
            return Money(BigInteger.valueOf(value))
        }

        fun add(a: Money, b: Money): Money {
            return Money(a.amount.add(b.amount))
        }

        fun subtract(a: Money, b: Money): Money {
            return Money(a.amount.subtract(b.amount))
        }
    }

    // Methods

    fun isPositiveOrZero(): Boolean {
        return amount >= BigInteger.ZERO
    }

    fun isNegative(): Boolean {
        return amount < BigInteger.ZERO
    }

    fun isPositive(): Boolean {
        return amount > BigInteger.ZERO
    }

    fun isGreaterThanOrEqualTo(money: Money): Boolean {
        return amount >= money.amount
    }

    fun isGreaterThan(money: Money): Boolean {
        return amount.compareTo(money.amount) >= 1
    }

    fun minus(money: Money): Money {
        return Money(amount.subtract(money.amount))
    }

    fun plus(money: Money): Money {
        return Money(amount.add(money.amount))
    }

    fun negate(): Money {
        return Money(amount.negate())
    }
}
