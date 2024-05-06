package buckpal.inbound.adapter.rest

import buckpal.application.SendMoneyCommand
import buckpal.application.SendMoneyUseCase
import buckpal.domain.ar.AccountId
import buckpal.domain.vo.Money
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@Controller("/accounts")
internal open class SendMoneyController(private val sendMoneyUseCase: SendMoneyUseCase) {

    @Post("/send/{sourceAccountId}/{targetAccountId}/{amount}")
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long,
        @PathVariable("targetAccountId") targetAccountId: Long,
        @PathVariable("amount") amount: Long,
    ) {
        println("Steve2: " + Thread.currentThread().toString())

        val command = SendMoneyCommand(
            AccountId(sourceAccountId),
            AccountId(targetAccountId),
            Money.of(amount)
        )

        sendMoneyUseCase.sendMoney(command)
    }

}
