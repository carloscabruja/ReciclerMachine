package es.refil.reciclermachine.presentation.screens.bottles.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import es.refil.reciclermachine.components.ProgressBar
import es.refil.reciclermachine.domain.model.Response
import es.refil.reciclermachine.presentation.screens.bottles.BottlesViewModel

@Composable
fun AddBottle(
    viewModel: BottlesViewModel = hiltViewModel()
) {
    when (val addBottleResponse = viewModel.addBottleResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> Unit
        is Response.Failure -> Log.e("AddBottle", addBottleResponse.e.toString())
    }
}