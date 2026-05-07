package com.budifinance.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budifinance.app.data.local.dao.TransactionDao
import com.budifinance.app.data.local.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
