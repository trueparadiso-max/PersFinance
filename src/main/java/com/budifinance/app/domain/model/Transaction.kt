package com.budifinance.app.domain.model

enum class TransactionType {
    INCOME, EXPENSE
}

data class Transaction(
    val id: Long,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: Long,
    val note: String?
)
