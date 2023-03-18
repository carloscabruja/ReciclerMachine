package es.refil.reciclermachine.presentation.screens.bottles

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.refil.reciclermachine.components.TopBar
import es.refil.reciclermachine.presentation.screens.bottles.components.AddBottle
import es.refil.reciclermachine.presentation.screens.bottles.components.AddBottleFloatingActionButton
import es.refil.reciclermachine.presentation.screens.bottles.components.CameraPreview
import es.refil.reciclermachine.presentation.screens.bottles.components.FloatingForm

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottlesScreen(
    viewModel: BottlesViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = { TopBar() },
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.openCamera() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Take a picture")
                }
            }

            CameraPreview()
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