package com.budifinance.app.domain.usecase

import com.budifinance.app.domain.model.Transaction
import com.budifinance.app.domain.repository.TransactionRepository

class AddTransactionUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction) {
        if (transaction.amount <= 0) throw IllegalArgumentException("Amount must be greater than zero")
        repository.addTransaction(transaction)
    }
}
