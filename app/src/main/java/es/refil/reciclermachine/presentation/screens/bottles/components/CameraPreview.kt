package es.refil.reciclermachine.presentation.screens.bottles.components

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import es.refil.reciclermachine.R
import es.refil.reciclermachine.presentation.screens.bottles.BottlesViewModel

@Composable
fun CameraPreview(
    viewModel: BottlesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    if (state.value.isCameraVisible) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp

        var previewView: PreviewView

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight.dp * 0.8f),
            ) {
                AndroidView(
                    factory = {
                        previewView = PreviewView(it)
                        viewModel.showCameraPreview(previewView, lifecycleOwner)
                        previewView
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight.dp * 0.8f)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight.dp * 0.2f),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = {
                    viewModel.captureAndSaveImage(context)
                }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_baseline_photo_camera_24
                        ),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}