package es.refil.reciclermachine.presentation.screens.bottles

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import es.refil.reciclermachine.R
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
    val state = viewModel.state.collectAsState()

    val painter: AsyncImagePainter = if (state.value.imageUriDetected != Uri.EMPTY) {
        rememberAsyncImagePainter(state.value.imageUriDetected)
    } else {
        rememberAsyncImagePainter(R.drawable.recicler_machine_placeholder)
    }

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
                Image(
                    painter = painter,
                    contentDescription = "Image detected",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "BARCODE: ${state.value.barCode}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "USER: ${state.value.user}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.openCamera() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Take a picture")
                }
                Button(
                    onClick = { viewModel.startScanning() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Scan a barcode")
                }
                Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Process")
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