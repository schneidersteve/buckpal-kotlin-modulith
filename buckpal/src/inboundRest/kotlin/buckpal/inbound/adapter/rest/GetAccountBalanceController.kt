package buckpal.inbound.adapter.rest

import buckpal.application.GetAccountBalanceQuery
import buckpal.domain.ar.AccountId
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/accounts")
//@ExecuteOn(BLOCKING)
internal open class GetAccountBalanceController(private val getAccountBalanceQuery: GetAccountBalanceQuery) {

    @Get("/{accountId}/balance", produces = [MediaType.TEXT_PLAIN])
    fun getAccountBalance(
        @PathVariable("accountId") accountId: Long,
    ): Long {
        println("Steve1: " + Thread.currentThread().toString())

        return getAccountBalanceQuery.getAccountBalance(AccountId(accountId)).amount.toLong()
    }

}
