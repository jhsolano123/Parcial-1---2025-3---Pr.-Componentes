/*package com.f1pitstop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.f1pitstop.app.PitStopApplication
import com.f1pitstop.app.ui.theme.F1PitStopTheme
import com.f1pitstop.app.ui.form.PitStopFormScreen
import com.f1pitstop.app.ui.form.PitStopFormUiEvent
import com.f1pitstop.app.ui.form.PitStopFormViewModel

private const val EXTRA_PIT_STOP_ID = "pit_stop_id"

/**
 * MainActivity principal de la aplicación F1 Pit Stop
 *
 * Nota: Esta es una implementación básica para que el proyecto compile.
 * La UI completa será implementada por las Personas 2 y 3 del equipo.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1PitStopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obtener el repositorio de la clase Application
                    val application = application as PitStopApplication
                    val repository = application.repository

                    // Crear el ViewModel usando el Factory
                    val summaryViewModel: SummaryViewModel = viewModel(factory = SummaryViewModel.Factory(repository))

                    SummaryScreen(summaryViewModel)
                    Greeting("F1 Pit Stop App")
                    PitStopFormRoute(activity = this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenido a $name",
        modifier = modifier
                private fun PitStopFormRoute(activity: Activity) {
            val application = activity.application as PitStopApplication
            val pitStopId = activity.intent?.getLongExtra(EXTRA_PIT_STOP_ID, 0L)?.takeIf { it > 0 }
            val viewModel: PitStopFormViewModel = viewModel(
                factory = PitStopFormViewModel.provideFactory(application.repository, pitStopId)
            )
        }
        val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        F1PitStopTheme {
            Greeting("F1 Pit Stop App")
            LaunchedEffect(Unit) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        PitStopFormUiEvent.Saved -> {
                            activity.finish()
                        }
                        is PitStopFormUiEvent.ShowMessage -> {
                            Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        PitStopFormScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }*/
// app/src/main/java/com/f1pitstop/app/MainActivity.kt
package com.f1pitstop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.f1pitstop.app.ui.AppNav
import com.f1pitstop.app.ui.theme.F1PitStopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1PitStopTheme {
                AppNav()
            }
        }
    }
}
