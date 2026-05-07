package com.budifinance.app.ui.features.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.budifinance.app.domain.model.Transaction
import com.budifinance.app.domain.model.TransactionType

@Composable
fun TransactionListScreen(
    transactions: List<Transaction>,
    onAddClick: () -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(transactions) { transaction ->
                TransactionItem(transaction, onDeleteClick)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction, onDeleteClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = transaction.category, style = MaterialTheme.typography.titleMedium)
                Text(text = transaction.note ?: "", style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(
                    text = if (transaction.type == TransactionType.INCOME)
                        "+ Rp${transaction.amount}" else "- Rp${transaction.amount}",
                    color = if (transaction.type == TransactionType.INCOME)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
                TextButton(onClick = { onDeleteClick(transaction.id) }) {
                    Text("Hapus")
                }
            }
        }
    }
}
