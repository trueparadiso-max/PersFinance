package com.budifinance.app.domain.usecase

import com.budifinance.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTotalBalanceUseCase(private val repository: TransactionRepository) {
    operator fun invoke(): Flow<Double> {
        // Kita akan mengimplementasikan logika kalkulasi real-time di repository
        // namun UseCase ini akan menyediakan stream-nya ke ViewModel
        return repository.getAllTransactions().map { transactions ->
            transactions.fold(0.0) { acc, transaction ->
                if (transaction.id == 0L) acc // safety check
                else if (transaction.type == com.budifinance.app.domain.model.TransactionType.INCOME)
                    acc + transaction.amount
                else
                    acc - transaction.amount
            }
        }
    }
}
