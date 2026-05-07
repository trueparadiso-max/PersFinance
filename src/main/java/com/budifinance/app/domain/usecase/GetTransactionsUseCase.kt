package com.budifinance.app.domain.usecase

import com.budifinance.app.domain.model.Transaction
import com.budifinance.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(private val repository: TransactionRepository) {
    operator fun invoke(): Flow<List<Transaction>> = repository.getAllTransactions()
}
