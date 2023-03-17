package es.refil.reciclermachine.presentation.screens.barcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.reciclermachine.domain.use_case.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarCodeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = MutableStateFlow(BarCodeState())
    val state = _state.asStateFlow()

    fun startScanning() {
        viewModelScope.launch {
            useCases.startScanning().collect {
                if (!it.isNullOrBlank()) {
                    _state.value = state.value.copy(
                        barCode = it
                    )
                }
            }
        }
    }
}