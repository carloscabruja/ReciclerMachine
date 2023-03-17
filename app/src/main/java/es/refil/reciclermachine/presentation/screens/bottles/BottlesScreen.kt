package es.refil.reciclermachine.presentation.screens.bottles

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.refil.reciclermachine.components.TopBar
import es.refil.reciclermachine.presentation.screens.bottles.components.AddBottle
import es.refil.reciclermachine.presentation.screens.bottles.components.AddBottleFloatingActionButton
import es.refil.reciclermachine.presentation.screens.bottles.components.FloatingForm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottlesScreen(
    viewModel: BottlesViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = { TopBar() },
        content = { padding ->
            FloatingForm()
        },
        floatingActionButton = {
            AddBottleFloatingActionButton(
                openDialog = { viewModel.openDialog() }
            )
        }
    )
    AddBottle()
}