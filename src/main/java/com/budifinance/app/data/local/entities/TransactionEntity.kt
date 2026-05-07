package com.budifinance.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionType {
    INCOME, EXPENSE
}

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: Long, // Timestamp in milliseconds
    val note: String?,
    val createdAt: Long = System.currentTimeMillis()
)
