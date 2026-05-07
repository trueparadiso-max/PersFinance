package com.budifinance.app.ui.features.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.budifinance.app.domain.model.Transaction
import com.budifinance.app.domain.model.TransactionType

@Composable
fun AddTransactionScreen(
    onSave: (Transaction) -> Unit,
    onCancel: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Tambah Transaksi", style = MaterialTheme.typography.headlineMedium)

        Row {
            BoutonToggle(
                label = "Pengeluaran",
                isSelected = type == TransactionType.EXPENSE,
                onClick = { type = TransactionType.EXPENSE }
            )
            Spacer(modifier = Modifier.width(8.dp))
            BoutonToggle(
                label = "Pemasukan",
                isSelected = type == TransactionType.INCOME,
                onClick = { type = TransactionType.INCOME }
            )
        }

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Jumlah (Rp)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Kategori (misal: Makan, Gaji)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Catatan (Opsional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val transaction = Transaction(
                    id = 0,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    type = type,
                    category = category,
                    date = System.currentTimeMillis(),
                    note = note
                )
                onSave(transaction)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Transaksi")
        }

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Batal")
        }
    }
}

@Composable
fun BoutonToggle(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(label)
    }
}
