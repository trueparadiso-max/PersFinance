package com.budifinance.app.util

import android.content.Context
import android.os.Environment
import com.budifinance.app.domain.model.Transaction
import java.io.File
import java.io.FileWriter
import java.io.IOException

object ExcelExportUtils {

    // Using CSV format as it is natively compatible with Excel and more lightweight for Android
    fun generateFinanceCsv(context: Context, transactions: List<Transaction>, reportTitle: String): File? {
        val fileName = "Finance_Report.csv"
        val file = File(context.cacheDir, fileName)

        try {
            FileWriter(file).use { writer ->
                // Header
                writer.append("$reportTitle\n")
                writer.append("Kategori,Tipe,Jumlah,Tanggal,Catatan\n")

                // Data rows
                transactions.forEach { tx ->
                    val type = if (tx.type == com.budifinance.app.domain.model.TransactionType.INCOME) "Pemasukan" else "Pengeluaran"
                    writer.append("${tx.category},$type,${tx.amount},${tx.date},${tx.note ?: ""}\n")
                }

                // Footer (Calculations)
                val totalIncome = transactions.filter { it.type == com.budifinance.app.domain.model.TransactionType.INCOME }.sumOf { it.amount }
                val totalExpense = transactions.filter { it.type == com.budifinance.app.domain.model.TransactionType.EXPENSE }.sumOf { it.amount }
                writer.append("\nTotal Pemasukan, ,$totalIncome, , \n")
                writer.append("Total Pengeluaran, ,$totalExpense, , \n")
                writer.append("Sisa Saldo, ,${totalIncome - totalExpense}, , \n")
            }
            return file
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
