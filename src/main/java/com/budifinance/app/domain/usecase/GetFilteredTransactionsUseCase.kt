package com.budifinance.app.domain.usecase

import com.budifinance.app.domain.model.Transaction
import com.budifinance.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetFilteredTransactionsUseCase(private val repository: TransactionRepository) {
    operator fun invoke(start: Long, end: Long): Flow<List<Transaction>> {
        return repository.getTransactionsByDateRange(start, end)
    }
}
