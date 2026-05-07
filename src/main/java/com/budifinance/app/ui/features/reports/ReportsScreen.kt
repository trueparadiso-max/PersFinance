package com.budifinance.app.ui.features.reports

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.budifinance.app.domain.model.Transaction

@Composable
fun ReportsScreen(
    selectedRange: String, // "Weekly", "Monthly", "Custom"
    onRangeChange: (String) -> Unit,
    onExportPdf: () -> Unit,
    onExportExcel: () -> Unit,
    onShareReport: () -> Unit,
    summaryData: ReportSummary
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = "Laporan Keuangan", style = MaterialTheme.typography.headlineMedium)

        // Range Selector
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Mingguan", "Bulanan", "Kustom").forEach { range ->
                FilterChip(
                    selected = selectedRange == range,
                    onClick = { onRangeChange(range) },
                    label = { Text(range) }
                )
            }
        }

        // Summary Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Ringkasan Periode", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Total Pemasukan: Rp ${summaryData.totalIncome}")
                Text(text = "Total Pengeluaran: Rp ${summaryData.totalExpense}")
                Text(
                    text = "Saldo Neto: Rp ${summaryData.netBalance}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }
        }

        // Export Actions
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Ekspor & Bagikan", style = MaterialTheme.typography.titleMedium)

            Button(
                onClick = onExportPdf,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan sebagai PDF")
            }

            Button(
                onClick = onExportExcel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan sebagai Excel (.xlsx)")
            }

            OutlinedButton(
                onClick = onShareReport,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Bagikan ke WhatsApp / Telegram")
            }
        }
    }
}

data class ReportSummary(
    val totalIncome: Double,
    val totalExpense: Double,
    val netBalance: Double
)
