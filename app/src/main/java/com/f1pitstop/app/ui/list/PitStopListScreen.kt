
// app/src/main/java/com/f1pitstop/app/ui/list/PitStopListScreen.kt
package com.f1pitstop.app.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.f1pitstop.app.R
import com.f1pitstop.app.data.model.PitStop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PitStopListScreen(
    onBack: () -> Unit,
    onNew: () -> Unit,
    onEdit: (Long) -> Unit,
    vm: PitStopListViewModel = viewModel()
) {
    val items by vm.items.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var deleteId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.listado_pit_stops)) },
                navigationIcon = { /* si quieres back */ },
                actions = { TextButton(onClick = onNew){ Text(stringResource(R.string.registrar_pit_stop)) } }
            )
        }
    ) { p ->
        Column(Modifier.padding(p).fillMaxSize()) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    vm.onQueryChange(it.text)
                },
                placeholder = { Text(stringResource(R.string.buscar)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.pit_stop_list_empty))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        PitStopRow(
                            item = item,
                            onEdit = { onEdit(item.id) },
                            onDelete = { deleteId = item.id }
                        )
                    }
                }
            }
        }
    }

    if (deleteId != null) {
        val current = items.firstOrNull { it.id == deleteId }
        AlertDialog(
            onDismissRequest = { deleteId = null },
            confirmButton = {
                TextButton(onClick = { vm.deleteById(deleteId!!); deleteId = null }) {
                    Text(stringResource(R.string.pit_stop_delete_confirm))
                }
            },
            dismissButton = { TextButton(onClick = { deleteId = null }) { Text(stringResource(R.string.cancelar)) } },
            title = { Text(stringResource(R.string.pit_stop_delete_title)) },
            text  = { Text(stringResource(R.string.pit_stop_delete_message, current?.piloto ?: "")) }
        )
    }
}

@Composable
private fun PitStopRow(
    item: PitStop,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("${item.piloto} • ${item.escuderia.displayName}", style = MaterialTheme.typography.titleMedium)
                Text("${item.getFechaFormateada()} • ${item.getTiempoFormateado()} • ${item.estado.displayName}",
                    style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onEdit)  { Icon(Icons.Default.Edit,  contentDescription = "Editar") }
            IconButton(onClick = onDelete){ Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.pit_stop_delete_action)) }
        }
    }
}
