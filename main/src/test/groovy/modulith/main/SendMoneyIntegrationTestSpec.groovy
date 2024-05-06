package modulith.main

import buckpal.application.LoadAccountPort
import buckpal.domain.ar.Account
import buckpal.domain.ar.AccountId
import buckpal.domain.vo.Money
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.BuildersKt
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

@MicronautTest(transactional = false)
class SendMoneyIntegrationTestSpec extends Specification {

    @Shared
    @AutoCleanup
    @Inject
    @Client("/accounts")
    HttpClient client

    @Shared
    @Inject
    LoadAccountPort loadAccountPort

    def "Send Money"() {
        given: "initial source account balance"
            var sourceAccountId = new AccountId(1L)
            Account sourceAccount = BuildersKt.runBlocking(
                EmptyCoroutineContext.INSTANCE,
                (scope, continuation) -> loadAccountPort.loadAccount(sourceAccountId, LocalDateTime.now())
            )
            var initialSourceBalance = sourceAccount.calculateBalance()

        and: "initial target account balance"
            var targetAccountId = new AccountId(2L)
            Account targetAccount = BuildersKt.runBlocking(
                EmptyCoroutineContext.INSTANCE,
                (scope, continuation) -> loadAccountPort.loadAccount(targetAccountId, LocalDateTime.now())
            )
            var initialTargetBalance = targetAccount.calculateBalance()
        and:
            var money = Money.@Companion.of(500L)

        when: "money is send"
            HttpResponse response = client.toBlocking().exchange(HttpRequest.POST("""/send/$sourceAccountId.value/$targetAccountId.value/$money.amount""", ""))

            sourceAccount = BuildersKt.runBlocking(
                EmptyCoroutineContext.INSTANCE,
                (scope, continuation) -> loadAccountPort.loadAccount(sourceAccountId, LocalDateTime.now())
            )
            targetAccount = BuildersKt.runBlocking(
                EmptyCoroutineContext.INSTANCE,
                (scope, continuation) -> loadAccountPort.loadAccount(targetAccountId, LocalDateTime.now())
            )

        then: "http status is OK"
            response.status == HttpStatus.OK

        and: "source account balance is correct"
            sourceAccount.calculateBalance() == initialSourceBalance.minus(money)

        and: "target account balance is correct"
            targetAccount.calculateBalance() == initialTargetBalance.plus(money)
    }

}
