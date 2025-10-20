// app/src/main/java/com/f1pitstop/app/ui/Nav.kt
package com.f1pitstop.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.f1pitstop.app.PitStopApplication
import com.f1pitstop.app.ui.edit.EditPitStopScreen
import com.f1pitstop.app.ui.list.PitStopListScreen
import com.f1pitstop.app.ui.summary.SummaryScreen
import com.f1pitstop.app.ui.summary.SummaryViewModel

object Routes {
    const val SUMMARY = "summary"
    const val LIST = "list"
    const val EDIT = "edit"
    const val EDIT_WITH_ID = "edit/{id}"
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as PitStopApplication
    val repository = application.repository
    
    NavHost(navController = nav, startDestination = Routes.SUMMARY) {
        composable(Routes.SUMMARY) {
            val summaryViewModel: SummaryViewModel = viewModel(
                factory = SummaryViewModel.Factory(repository)
            )
            SummaryScreen(
                onNew = { nav.navigate(Routes.EDIT) },
                onList = { nav.navigate(Routes.LIST) },
                viewModel = summaryViewModel
            )
        }
        composable(Routes.LIST) {
            PitStopListScreen(
                onBack = { nav.popBackStack() },
                onNew = { nav.navigate(Routes.EDIT) },
                onEdit = { id -> nav.navigate("edit/$id") }
            )
        }
        composable(Routes.EDIT) {
            EditPitStopScreen(onDone = { nav.popBackStack() })
        }
        composable(
            route = Routes.EDIT_WITH_ID,
            arguments = listOf(navArgument("id"){ type = NavType.LongType })
        ) { e ->
            val id = e.arguments?.getLong("id")!!
            EditPitStopScreen(existingId = id, onDone = { nav.popBackStack() })
        }
    }
}