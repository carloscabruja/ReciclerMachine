package es.refil.reciclermachine.presentation.screens.bottles.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.refil.reciclermachine.core.Constants.ADD
import es.refil.reciclermachine.core.Constants.ADD_BOTTLE
import es.refil.reciclermachine.core.Constants.DISMISS
import es.refil.reciclermachine.core.Constants.POINTS
import es.refil.reciclermachine.core.Constants.SIZE
import es.refil.reciclermachine.core.Constants.TYPE
import es.refil.reciclermachine.presentation.screens.bottles.BottlesViewModel
import kotlinx.coroutines.job

@Composable
fun FloatingForm(
    viewModel: BottlesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    if (state.value.openDialog) {
        var type by remember { mutableStateOf("") }
        var points by remember { mutableStateOf("") }
        var size by remember { mutableStateOf("") }
        val focusRequester = FocusRequester()

        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            title = { Text(text = ADD_BOTTLE) },
            text = {
                Column {
                    Text(
                        text = state.value.barCode,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { viewModel.startScanning() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Scan barcode")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = type,
                        onValueChange = { type = it },
                        placeholder = { Text(text = TYPE) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = points,
                        onValueChange = { points = it },
                        placeholder = { Text(text = POINTS) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = size,
                        onValueChange = { size = it },
                        placeholder = { Text(text = SIZE) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    // Trim to avoid empty strings
                    type = type.trim()
                    points = points.trim()
                    size = size.trim()

                    // Convert to Int
                    val bottleType: Int = if (type.isNotEmpty()) type.toInt() else -1
                    val bottlePoints: Int = if (points.isNotEmpty()) points.toInt() else -1
                    val bottleSize: Int = if (size.isNotEmpty()) size.toInt() else -1
                    viewModel.addBottle(state.value.barCode, bottleType, bottlePoints, bottleSize)

                    viewModel.closeDialog()
                }) {
                    Text(text = ADD)
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.closeDialog() }) {
                    Text(text = DISMISS)
                }
            }
        )
    }
}