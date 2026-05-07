package com.budifinance.app.data.repository

import com.budifinance.app.data.local.dao.TransactionDao
import com.budifinance.app.data.local.entities.TransactionEntity
import com.budifinance.app.domain.model.Transaction
import com.budifinance.app.domain.model.TransactionType
import com.budifinance.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val dao: TransactionDao) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsByDateRange(start: Long, end: Long): Flow<List<Transaction>> {
        return dao.getTransactionsByDateRange(start, end).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transactionId: Long) {
        dao.deleteById(transactionId)
    }

    override suspend fun getTotalBalance(): Double {
        val income = dao.getTotalIncome() ?: 0.0
        val expense = dao.getTotalExpense() ?: 0.0
        return income - expense
    }

    private fun TransactionEntity.toDomain() = Transaction(
        id = id,
        amount = amount,
        type = TransactionType.valueOf(this.type.name),
        category = category,
        date = date,
        note = note
    )

    private fun Transaction.toEntity() = TransactionEntity(
        id = id,
        amount = amount,
        type = com.budifinance.app.data.local.entities.TransactionType.valueOf(this.type.name),
        category = category,
        date = date,
        note = note
    )
}
