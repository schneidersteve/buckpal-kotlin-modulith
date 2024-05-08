package buckpal.outbound.adapter.persistence

import buckpal.domain.ar.Account
import buckpal.domain.ar.AccountId
import buckpal.domain.vo.ActivityWindow
import buckpal.domain.vo.Money
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

import static buckpal.testdata.AccountTestDataKt.defaultAccount
import static buckpal.testdata.ActivityTestDataKt.defaultActivity

@MicronautTest(transactional = false)
class AccountPersistenceAdapterSpec extends Specification {

    AccountRepository accountRepository = Mock()

    ActivityRepository activityRepository = Mock()

    AccountPersistenceAdapter adapterUnderTest

    @Shared
    @Inject
    AccountMapper accountMapper

    def setup() {
        adapterUnderTest = new AccountPersistenceAdapter(accountRepository, activityRepository, accountMapper)
    }

    def "loads Account"() {
        given:
            var accountId = new AccountId(1L)
            var baselineDate = LocalDateTime.of(2018, 8, 10, 0, 0)
            accountRepository.findById(accountId.value) >> Optional.of(new AccountEntity(1L))
            activityRepository.findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(accountId.value, baselineDate) >> [
                new ActivityEntity(
                    5,
                    LocalDateTime.of(2019, 8, 9, 9, 0),
                    1,
                    1,
                    2,
                    1000
                ),
                new ActivityEntity(
                    7,
                    LocalDateTime.of(2019, 8, 9, 10, 0),
                    1,
                    2,
                    1,
                    1000
                )
            ]
            activityRepository.getWithdrawalBalanceUntil(accountId.value, baselineDate) >> 500L
            activityRepository.getDepositBalanceUntil(accountId.value, baselineDate) >> 1000L
        when:
            Account account = adapterUnderTest.loadAccount(
                accountId,
                baselineDate
            )
        then:
            account.getActivityWindow().getActivities().size() == 2
        and:
            account.calculateBalance() == Money.@Companion.of(500L)
    }

    def "updates Activities"() {
        given:
            Account account = defaultAccount()
                .withBaselineBalance(Money.@Companion.of(555L))
                .withActivityWindow(new ActivityWindow(
                    defaultActivity()
                        .withId(null)
                        .withMoney(Money.@Companion.of(1L))
                        .build()))
                .build()
        when:
            adapterUnderTest.updateActivities(account)
        then:
            1 * activityRepository.save(_)
    }

}
