// app/src/main/java/com/f1pitstop/app/ui/edit/EditPitStopScreen.kt
package com.f1pitstop.app.ui.edit




import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.f1pitstop.app.R
import com.f1pitstop.app.data.model.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPitStopScreen(
    existingId: Long? = null,
    onDone: () -> Unit,
    vm: EditPitStopViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var piloto by remember { mutableStateOf("") }
    var escuderia by remember { mutableStateOf(Escuderia.MERCEDES) }
    var tiempo by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf(TipoNeumatico.SOFT) }
    var numNeum by remember { mutableStateOf("4") }
    var estado by remember { mutableStateOf(EstadoPitStop.OK) }
    var motivo by remember { mutableStateOf("") }
    var mecanico by remember { mutableStateOf("") }

    LaunchedEffect(existingId) {
        if (existingId != null) {
            vm.load(existingId)?.let { p ->
                piloto = p.piloto
                escuderia = p.escuderia
                tiempo = p.tiempoTotal.toString()
                tipo = p.cambioNeumaticos
                numNeum = p.numeroNeumaticosCambiados.toString()
                estado = p.estado
                motivo = p.motivoFallo.orEmpty()
                mecanico = p.mecanicoPrincipal
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(if (existingId == null) stringResource(R.string.registrar_pit_stop) else "Editar Pit Stop") })
    }) { p ->
        Column(Modifier.padding(p).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(piloto, { piloto = it }, label = { Text(stringResource(R.string.piloto)) }, modifier = Modifier.fillMaxWidth())
            EscuderiaDropdown(escuderia) { escuderia = it }
            OutlinedTextField(tiempo, { tiempo = it }, label = { Text(stringResource(R.string.tiempo_total)) }, modifier = Modifier.fillMaxWidth())
            TipoDropdown(tipo) { tipo = it }
            OutlinedTextField(numNeum, { numNeum = it }, label = { Text(stringResource(R.string.numero_neumaticos)) }, modifier = Modifier.fillMaxWidth())
            EstadoDropdown(estado) { estado = it }
            OutlinedTextField(motivo, { motivo = it }, label = { Text(stringResource(R.string.motivo_fallo)) }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(mecanico, { mecanico = it }, label = { Text(stringResource(R.string.mecanico_principal)) }, modifier = Modifier.fillMaxWidth())

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
                    scope.launch {
                        val entity = PitStop(
                            id = existingId ?: 0L,
                            piloto = piloto,
                            escuderia = escuderia,
                            tiempoTotal = tiempo.toDoubleOrNull() ?: 0.0,
                            cambioNeumaticos = tipo,
                            numeroNeumaticosCambiados = numNeum.toIntOrNull() ?: 0,
                            estado = estado,
                            motivoFallo = motivo.ifBlank { null },
                            mecanicoPrincipal = mecanico
                        )
                        vm.save(entity)
                        onDone()
                    }
                }) { Text(stringResource(R.string.guardar)) }

                OutlinedButton(onClick = onDone) { Text(stringResource(R.string.cancelar)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EscuderiaDropdown(value: Escuderia, onChange: (Escuderia) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = value.displayName, onValueChange = {}, readOnly = true,
            label = { Text(stringResource(R.string.escuderia)) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Escuderia.values().forEach {
                DropdownMenuItem(text = { Text(it.displayName) }, onClick = { onChange(it); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TipoDropdown(value: TipoNeumatico, onChange: (TipoNeumatico) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = value.displayName, onValueChange = {}, readOnly = true,
            label = { Text(stringResource(R.string.cambio_neumaticos)) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            TipoNeumatico.values().forEach {
                DropdownMenuItem(text = { Text(it.displayName) }, onClick = { onChange(it); expanded = false })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EstadoDropdown(value: EstadoPitStop, onChange: (EstadoPitStop) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = value.displayName, onValueChange = {}, readOnly = true,
            label = { Text(stringResource(R.string.estado)) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            EstadoPitStop.values().forEach {
                DropdownMenuItem(text = { Text(it.displayName) }, onClick = { onChange(it); expanded = false })
            }
        }
    }
}
