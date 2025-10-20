package com.f1pitstop.app.ui.summary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import com.f1pitstop.app.data.model.PitStop
import com.f1pitstop.app.ui.theme.F1PitStopTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SummaryScreen(viewModel: SummaryViewModel = viewModel()) {
    val fastestPitStop by viewModel.fastestPitStop.collectAsState()
    val averagePitStopTime by viewModel.averagePitStopTime.collectAsState()
    val totalPitStopsCount by viewModel.totalPitStopsCount.collectAsState()
    val lastPitStopsForChart by viewModel.lastPitStopsForChart.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resumen de Pit Stops",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Estadísticas principales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticCard(
                title = "Pit Stop Más Rápido",
                value = fastestPitStop?.let { "%.1f s".format(it.tiempoTotal) } ?: "N/A",
                subtitle = fastestPitStop?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it.fechaHora)) } ?: ""
            )
            StatisticCard(
                title = "Promedio Pit Stop",
                value = averagePitStopTime?.let { "%.1f s".format(it) } ?: "N/A"
            )
            StatisticCard(
                title = "Total Pit Stops",
                value = totalPitStopsCount.toString()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Gráfico de barras de los últimos pit stops
        Text(
            text = "Últimos Pit Stops (Tiempo en segundos)",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LastPitStopsBarChart(lastPitStopsForChart)
    }
}

@Composable
fun StatisticCard(title: String, value: String, subtitle: String = "") {
    Card(
        modifier = Modifier.width(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
            if (subtitle.isNotEmpty()) {
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun LastPitStopsBarChart(pitStops: List<PitStop>) {
    val barData = pitStops.mapIndexed { index, pitStop ->
        BarData(
            point = Point(index.toFloat(), pitStop.tiempoTotal.toFloat()),
            color = if (pitStop.estado == com.f1pitstop.app.data.model.EstadoPitStop.OK) Color.Green else Color.Red,
            dataCategory = pitStop.piloto // Usar el piloto como categoría si es necesario, o un índice
        )
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .steps(barData.size - 1)
        .labelData { index -> pitStops.getOrNull(index)?.piloto ?: "" }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(5)
        .labelData { index -> (index * 2).toString() + "s" } // Ejemplo: 0s, 2s, 4s, ...
        .build()

    val barChartData = BarChartData(
        chartData = barData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barWidth = 20.dp,
        paddingAroundBars = 8.dp
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        BarChart(modifier = Modifier.fillMaxSize(), barChartData = barChartData)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSummaryScreen() {
    F1PitStopTheme {
        // Para el preview, se puede inyectar un ViewModel mock o usar datos de ejemplo
        // Aquí se usarán datos estáticos para la previsualización
        val samplePitStops = listOf(
            PitStop(id = 1, piloto = "Lewis", escuderia = com.f1pitstop.app.data.model.Escuderia.MERCEDES, tiempoTotal = 2.1, cambioNeumaticos = com.f1pitstop.app.data.model.TipoNeumatico.SOFT, numeroNeumaticosCambiados = 4, estado = com.f1pitstop.app.data.model.EstadoPitStop.OK, mecanicoPrincipal = "John", fechaHora = Date().time - 100000),
            PitStop(id = 2, piloto = "Max", escuderia = com.f1pitstop.app.data.model.Escuderia.RED_BULL, tiempoTotal = 2.5, cambioNeumaticos = com.f1pitstop.app.data.model.TipoNeumatico.MEDIUM, numeroNeumaticosCambiados = 4, estado = com.f1pitstop.app.data.model.EstadoPitStop.OK, mecanicoPrincipal = "Mike", fechaHora = Date().time - 80000),
            PitStop(id = 3, piloto = "Charles", escuderia = com.f1pitstop.app.data.model.Escuderia.FERRARI, tiempoTotal = 3.0, cambioNeumaticos = com.f1pitstop.app.data.model.TipoNeumatico.HARD, numeroNeumaticosCambiados = 4, estado = com.f1pitstop.app.data.model.EstadoPitStop.FALLIDO, motivoFallo = "Tuerca suelta", mecanicoPrincipal = "Tom", fechaHora = Date().time - 60000),
            PitStop(id = 4, piloto = "Fernando", escuderia = com.f1pitstop.app.data.model.Escuderia.ASTON_MARTIN, tiempoTotal = 2.3, cambioNeumaticos = com.f1pitstop.app.data.model.TipoNeumatico.SOFT, numeroNeumaticosCambiados = 4, estado = com.f1pitstop.app.data.model.EstadoPitStop.OK, mecanicoPrincipal = "David", fechaHora = Date().time - 40000),
            PitStop(id = 5, piloto = "George", escuderia = com.f1pitstop.app.data.model.Escuderia.MERCEDES, tiempoTotal = 2.8, cambioNeumaticos = com.f1pitstop.app.data.model.TipoNeumatico.MEDIUM, numeroNeumaticosCambiados = 4, estado = com.f1pitstop.app.data.model.EstadoPitStop.OK, mecanicoPrincipal = "Chris", fechaHora = Date().time - 20000)
        )

        // Se necesitaría un ViewModelFactory para inyectar un repositorio mock
        // Por simplicidad en el preview, se muestran solo los componentes estáticos
        Column {
            StatisticCard("Pit Stop Más Rápido", "2.1 s", "10/10/2025")
            StatisticCard("Promedio Pit Stop", "2.5 s")
            StatisticCard("Total Pit Stops", "15")
            LastPitStopsBarChart(samplePitStops)
        }
    }
}