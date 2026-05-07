package com.budifinance.app.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PdfDocument
import android.os.Environment
import android.util.Log
import com.budifinance.app.domain.model.Transaction
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfExportUtils {

    fun generateFinancePdf(context: Context, transactions: List<Transaction>, reportTitle: String): File? {
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val titlePaint = Paint().apply {
            color = Color.BLACK
            textSize = 20f
            isFakeBoldText = true
        }
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }

        val pageInfo = PdfDocument.PageInfo.Builder()
            .setPageSize(595, 842) // A4 size
            .build()

        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        var yPosition = 50f

        // Draw Title
        canvas.drawText(reportTitle, 200f, yPosition, titlePaint)
        yPosition += 40f

        // Draw Table Header
        canvas.drawText("Kategori", 50f, yPosition, textPaint)
        canvas.drawText("Jumlah", 400f, yPosition, textPaint)
        yPosition += 20f
        canvas.drawLine(50f, yPosition, 500f, yPosition, paint)
        yPosition += 20f

        var totalIncome = 0.0
        var totalExpense = 0.0

        // Draw Transactions
        transactions.forEach { tx ->
            val typeText = if (tx.type == com.budifinance.app.domain.model.TransactionType.INCOME) "Pemasukan" else "Pengeluaran"
            canvas.drawText("${tx.category} ($typeText)", 50f, yPosition, textPaint)
            canvas.drawText("Rp ${tx.amount}", 400f, yPosition, textPaint)

            if (tx.type == com.budifinance.app.domain.model.TransactionType.INCOME) totalIncome += tx.amount else totalExpense += tx.amount

            yPosition += 20f
            if (yPosition > 800f) {
                pdfDocument.finishPage(page)
                // In a real app, handle multi-page overflow here
            }
        }

        yPosition += 20f
        canvas.drawLine(50f, yPosition, 500f, yPosition, paint)
        yPosition += 20f
        canvas.drawText("Total Pemasukan: Rp $totalIncome", 50f, yPosition, textPaint)
        yPosition += 20f
        canvas.drawText("Total Pengeluaran: Rp $totalExpense", 50f, yPosition, textPaint)
        yPosition += 20f
        canvas.drawText("Sisa Saldo: Rp ${totalIncome - totalExpense}", 50f, yPosition, textPaint)

        pdfDocument.finishPage(page)

        return try {
            val file = File(context.cacheDir, "Finance_Report.pdf")
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()
            file
        } catch (e: IOException) {
            Log.e("PdfExport", "Error generating PDF", e)
            pdfDocument.close()
            null
        }
    }
}
