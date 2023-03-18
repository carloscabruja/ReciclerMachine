package es.refil.reciclermachine.presentation.screens.bottles

import android.content.Context
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.reciclermachine.domain.model.Bottle
import es.refil.reciclermachine.domain.model.Response
import es.refil.reciclermachine.domain.repository.AddBottleResponse
import es.refil.reciclermachine.domain.use_case.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottlesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = MutableStateFlow(BottlesState())
    val state = _state.asStateFlow()

    var addBottleResponse by mutableStateOf<AddBottleResponse>(Response.Success(false))
        private set


    fun addBottle(code: String, type: Int?, points: Int?, size: Int?) = viewModelScope.launch {
        addBottleResponse = Response.Loading
        addBottleResponse = useCases.addBottle(Bottle(code, type, points, size))
    }

    fun openDialog() {
        _state.value = state.value.copy(
            openDialog = true
        )
    }

    fun closeDialog() {
        _state.value = state.value.copy(
            openDialog = false
        )
    }

    fun startScanning() = viewModelScope.launch {
        useCases.startScanning().collect {
            if (!it.isNullOrBlank()) {
                _state.value = state.value.copy(
                    barCode = it
                )
            }
        }

    }

    fun openCamera() {
        _state.value = state.value.copy(
            isCameraVisible = true
        )
    }

    private fun closeCamera() {
        _state.value = state.value.copy(
            isCameraVisible = false
        )
    }

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) = viewModelScope.launch {
        useCases.showCameraPreview(previewView, lifecycleOwner)
    }

    fun captureAndSaveImage(context: Context) = viewModelScope.launch {
        useCases.captureAndSaveImage(context).collect {
            if (it != null) {
                _state.value = state.value.copy(
                    imageUriDetected = it
                )
            } else {
                Toast.makeText(
                    context,
                    "No se ha podido guardar la imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }.also {
                closeCamera()
            }
        }
    }
}