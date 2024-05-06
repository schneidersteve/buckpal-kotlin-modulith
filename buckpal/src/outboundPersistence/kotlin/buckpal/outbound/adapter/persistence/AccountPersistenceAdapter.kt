package buckpal.outbound.adapter.persistence

import buckpal.application.LoadAccountPort
import buckpal.application.UpdateAccountStatePort
import buckpal.domain.ar.Account
import buckpal.domain.ar.AccountId
import jakarta.inject.Singleton
import jakarta.persistence.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

@Singleton
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper,
) : LoadAccountPort,
    UpdateAccountStatePort {

    private val logger: Logger = LoggerFactory.getLogger(AccountPersistenceAdapter::class.java)

    override fun loadAccount(
        accountId: AccountId,
        baselineDate: LocalDateTime,
    ): Account {
        val account = accountRepository.findById(accountId.value).orElseThrow { EntityNotFoundException() }
        logger.debug("findById(id = $accountId) = $account");

        val activities =
            activityRepository.findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(
                accountId.value,
                baselineDate
            )
        logger.debug("findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(ownerAccountId = $accountId, timestamp = $baselineDate) = ${activities.toList()}");

        val withdrawalBalance = activityRepository.getWithdrawalBalanceUntil(accountId.value, baselineDate) ?: 0L
        logger.debug("getWithdrawalBalanceUntil(accountId = $accountId, until = $baselineDate) = $withdrawalBalance");

        val depositBalance = activityRepository.getDepositBalanceUntil(accountId.value, baselineDate) ?: 0L
        logger.debug("getDepositBalanceUntil(accountId = $accountId, until = $baselineDate) = $depositBalance");

        return accountMapper.mapToAccount(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        )
    }

    override fun updateActivities(account: Account) {
        account.activityWindow.activities.forEach { activity ->
            if (activity.id == null) {
                val ae = accountMapper.mapToActivityEntity(activity)
                logger.debug("save(entity = $ae)");
                activityRepository.save(ae)
            }
        }
    }
}
