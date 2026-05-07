package com.budifinance.app.data.local.dao

import androidx.room.*
import com.budifinance.app.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getTransactionsByDateRange(start: Long, end: Long): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT SUM(amount) FROM (SELECT amount FROM transactions WHERE type = 'INCOME')")
    suspend fun getTotalIncome(): Double?

    @Query("SELECT SUM(amount) FROM (SELECT amount FROM transactions WHERE type = 'EXPENSE')")
    suspend fun getTotalExpense(): Double?
}
